package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.web.base.pojo.PageInfo;
import com.y3tu.yao.upms.model.entity.Dict;
import com.y3tu.yao.upms.model.entity.DictData;
import com.y3tu.yao.upms.service.DictDataService;
import com.y3tu.yao.upms.service.DictService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典Controller
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController<DictService, Dict> {
    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @PostMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(dictService.page(pageInfo));
    }

    @PostMapping("/save")
    @Override
    public R save(@RequestBody Dict dict) {
        List<Dict> list = dictService.list(new QueryWrapper<Dict>().eq("code", dict.getCode()));
        if (list.size() > 0) {
            return R.error("字典编码已存在");
        }
        dictService.save(dict);
        return R.success();
    }

    @PutMapping("/update")
    @Override
    public R update(@RequestBody Dict dict) {
        Dict old = dictService.getById(dict.getId());
        // 若type修改判断唯一
        if (!old.getCode().equals(dict.getCode()) && dictService.list(new QueryWrapper<Dict>().eq("code", dict.getCode())).size() > 0) {
            return R.error("字典编码已存在");
        }
        dictService.updateById(dict);
        return R.success();
    }

    @DeleteMapping("/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            dictService.removeById(id);
            dictDataService.remove(new QueryWrapper<DictData>().eq("dict_id", id));
        }
        return R.success();
    }

    @GetMapping("/search")
    public R search(@RequestParam String key) {
        List<Dict> list = dictService.list(new QueryWrapper<Dict>().like("title", key).or().like("code", key));
        return R.success(list);
    }
}
