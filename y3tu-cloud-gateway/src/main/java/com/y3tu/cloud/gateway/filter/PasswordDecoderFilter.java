package com.y3tu.cloud.gateway.filter;

import com.y3tu.tool.core.codec.Base64Util;
import com.y3tu.tool.core.text.CharsetUtil;
import com.y3tu.tool.core.text.StringUtils;
import com.y3tu.tool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.util.Map;

/**
 * @author lengleng
 * @date 2018/7/15
 * 密码解密工具类
 */
@Slf4j
@Component
public class PasswordDecoderFilter extends AbstractGatewayFilterFactory {
	private static final String PASSWORD = "password";
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";
	@Value("${security.encode.key:1234567812345678}")
	private String encodeKey;

	private static String decryptAES(String data, String pass) throws Exception {
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		SecretKeySpec keyspec = new SecretKeySpec(pass.getBytes(), KEY_ALGORITHM);
		IvParameterSpec ivspec = new IvParameterSpec(pass.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		byte[] result = cipher.doFinal(Base64Util.decode(data.getBytes(CharsetUtil.UTF_8)));
		return new String(result, CharsetUtil.UTF_8);
	}

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// 不是登录请求，直接向下执行
			if (!StringUtils.containsIgnoreCase(request.getURI().getPath(), ImageCodeGatewayFilter.OAUTH_TOKEN_URL)) {
				return chain.filter(exchange);
			}

			URI uri = exchange.getRequest().getURI();
			String queryParam = uri.getRawQuery();
			Map<String, String> paramMap = HttpUtil.decodeParamMap(queryParam, CharsetUtil.UTF_8);

			String password = paramMap.get(PASSWORD);
			if (StringUtils.isNotBlank(password)) {
				try {
					password = decryptAES(password, encodeKey);
				} catch (Exception e) {
					log.error("密码解密失败:{}", password);
					return Mono.error(e);
				}
				paramMap.put(PASSWORD, password.trim());
			}

			URI newUri = UriComponentsBuilder.fromUri(uri)
				.replaceQuery(HttpUtil.toParams(paramMap))
				.build(true)
				.toUri();

			ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
			return chain.filter(exchange.mutate().request(newRequest).build());
		};
	}
}
