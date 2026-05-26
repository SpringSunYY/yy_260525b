package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.CourseLikeInfo;
import com.lz.manage.model.vo.courseLikeInfo.CourseLikeInfoVo;
import com.lz.manage.model.dto.courseLikeInfo.CourseLikeInfoQuery;
import com.lz.manage.model.dto.courseLikeInfo.CourseLikeInfoInsert;
import com.lz.manage.model.dto.courseLikeInfo.CourseLikeInfoEdit;
import com.lz.manage.service.ICourseLikeInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 课程点赞Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseLikeInfo")
public class CourseLikeInfoController extends BaseController
{
    @Resource
    private ICourseLikeInfoService courseLikeInfoService;

    /**
     * 查询课程点赞列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseLikeInfoQuery courseLikeInfoQuery)
    {
        CourseLikeInfo courseLikeInfo = CourseLikeInfoQuery.queryToObj(courseLikeInfoQuery);
        startPage();
        List<CourseLikeInfo> list = courseLikeInfoService.selectCourseLikeInfoList(courseLikeInfo);
        List<CourseLikeInfoVo> listVo= list.stream().map(CourseLikeInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出课程点赞列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:export')")
    @Log(title = "课程点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseLikeInfoQuery courseLikeInfoQuery)
    {
        CourseLikeInfo courseLikeInfo = CourseLikeInfoQuery.queryToObj(courseLikeInfoQuery);
        List<CourseLikeInfo> list = courseLikeInfoService.selectCourseLikeInfoList(courseLikeInfo);
        ExcelUtil<CourseLikeInfo> util = new ExcelUtil<CourseLikeInfo>(CourseLikeInfo.class);
        util.exportExcel(response, list, "课程点赞数据");
    }

    /**
     * 获取课程点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseLikeInfo courseLikeInfo = courseLikeInfoService.selectCourseLikeInfoById(id);
        return success(CourseLikeInfoVo.objToVo(courseLikeInfo));
    }

    /**
     * 新增课程点赞
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:add')")
    @Log(title = "课程点赞", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseLikeInfoInsert courseLikeInfoInsert)
    {
        CourseLikeInfo courseLikeInfo = CourseLikeInfoInsert.insertToObj(courseLikeInfoInsert);
        return toAjax(courseLikeInfoService.insertCourseLikeInfo(courseLikeInfo));
    }

    /**
     * 修改课程点赞
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:edit')")
    @Log(title = "课程点赞", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseLikeInfoEdit courseLikeInfoEdit)
    {
        CourseLikeInfo courseLikeInfo = CourseLikeInfoEdit.editToObj(courseLikeInfoEdit);
        return toAjax(courseLikeInfoService.updateCourseLikeInfo(courseLikeInfo));
    }

    /**
     * 删除课程点赞
     */
    @PreAuthorize("@ss.hasPermi('manage:courseLikeInfo:remove')")
    @Log(title = "课程点赞", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseLikeInfoService.deleteCourseLikeInfoByIds(ids));
    }
}
