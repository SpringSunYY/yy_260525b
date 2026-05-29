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
import com.lz.manage.model.domain.CourseMaterialInfo;
import com.lz.manage.model.vo.courseMaterialInfo.CourseMaterialInfoVo;
import com.lz.manage.model.dto.courseMaterialInfo.CourseMaterialInfoQuery;
import com.lz.manage.model.dto.courseMaterialInfo.CourseMaterialInfoInsert;
import com.lz.manage.model.dto.courseMaterialInfo.CourseMaterialInfoEdit;
import com.lz.manage.service.ICourseMaterialInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 课程资料Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseMaterialInfo")
public class CourseMaterialInfoController extends BaseController
{
    @Resource
    private ICourseMaterialInfoService courseMaterialInfoService;

    /**
     * 查询课程资料列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseMaterialInfoQuery courseMaterialInfoQuery)
    {
        CourseMaterialInfo courseMaterialInfo = CourseMaterialInfoQuery.queryToObj(courseMaterialInfoQuery);
        startPage();
        List<CourseMaterialInfo> list = courseMaterialInfoService.selectCourseMaterialInfoList(courseMaterialInfo);
        List<CourseMaterialInfoVo> listVo= list.stream().map(CourseMaterialInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:list')")
    @GetMapping("/list/home")
    public TableDataInfo home(CourseMaterialInfoQuery courseMaterialInfoQuery) {
        CourseMaterialInfo courseMaterialInfo = CourseMaterialInfoQuery.queryToObj(courseMaterialInfoQuery);
        startPage();
        List<CourseMaterialInfo> list = courseMaterialInfoService.selectCourseMaterialInfoListHome(courseMaterialInfo);
        List<CourseMaterialInfoVo> listVo = list.stream().map(CourseMaterialInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出课程资料列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:export')")
    @Log(title = "课程资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseMaterialInfoQuery courseMaterialInfoQuery)
    {
        CourseMaterialInfo courseMaterialInfo = CourseMaterialInfoQuery.queryToObj(courseMaterialInfoQuery);
        List<CourseMaterialInfo> list = courseMaterialInfoService.selectCourseMaterialInfoList(courseMaterialInfo);
        ExcelUtil<CourseMaterialInfo> util = new ExcelUtil<CourseMaterialInfo>(CourseMaterialInfo.class);
        util.exportExcel(response, list, "课程资料数据");
    }

    /**
     * 获取课程资料详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseMaterialInfo courseMaterialInfo = courseMaterialInfoService.selectCourseMaterialInfoById(id);
        return success(CourseMaterialInfoVo.objToVo(courseMaterialInfo));
    }

    /**
     * 新增课程资料
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:add')")
    @Log(title = "课程资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseMaterialInfoInsert courseMaterialInfoInsert)
    {
        CourseMaterialInfo courseMaterialInfo = CourseMaterialInfoInsert.insertToObj(courseMaterialInfoInsert);
        return toAjax(courseMaterialInfoService.insertCourseMaterialInfo(courseMaterialInfo));
    }

    /**
     * 修改课程资料
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:edit')")
    @Log(title = "课程资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseMaterialInfoEdit courseMaterialInfoEdit)
    {
        CourseMaterialInfo courseMaterialInfo = CourseMaterialInfoEdit.editToObj(courseMaterialInfoEdit);
        return toAjax(courseMaterialInfoService.updateCourseMaterialInfo(courseMaterialInfo));
    }

    /**
     * 删除课程资料
     */
    @PreAuthorize("@ss.hasPermi('manage:courseMaterialInfo:remove')")
    @Log(title = "课程资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseMaterialInfoService.deleteCourseMaterialInfoByIds(ids));
    }
}
