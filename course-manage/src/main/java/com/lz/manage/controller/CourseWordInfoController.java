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
import com.lz.manage.model.domain.CourseWordInfo;
import com.lz.manage.model.vo.courseWordInfo.CourseWordInfoVo;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoQuery;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoInsert;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoEdit;
import com.lz.manage.service.ICourseWordInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 课程作业Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseWordInfo")
public class CourseWordInfoController extends BaseController
{
    @Resource
    private ICourseWordInfoService courseWordInfoService;

    /**
     * 查询课程作业列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseWordInfoQuery courseWordInfoQuery)
    {
        CourseWordInfo courseWordInfo = CourseWordInfoQuery.queryToObj(courseWordInfoQuery);
        startPage();
        List<CourseWordInfo> list = courseWordInfoService.selectCourseWordInfoList(courseWordInfo);
        List<CourseWordInfoVo> listVo= list.stream().map(CourseWordInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出课程作业列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:export')")
    @Log(title = "课程作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseWordInfoQuery courseWordInfoQuery)
    {
        CourseWordInfo courseWordInfo = CourseWordInfoQuery.queryToObj(courseWordInfoQuery);
        List<CourseWordInfo> list = courseWordInfoService.selectCourseWordInfoList(courseWordInfo);
        ExcelUtil<CourseWordInfo> util = new ExcelUtil<CourseWordInfo>(CourseWordInfo.class);
        util.exportExcel(response, list, "课程作业数据");
    }

    /**
     * 获取课程作业详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseWordInfo courseWordInfo = courseWordInfoService.selectCourseWordInfoById(id);
        return success(CourseWordInfoVo.objToVo(courseWordInfo));
    }

    /**
     * 新增课程作业
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:add')")
    @Log(title = "课程作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseWordInfoInsert courseWordInfoInsert)
    {
        CourseWordInfo courseWordInfo = CourseWordInfoInsert.insertToObj(courseWordInfoInsert);
        return toAjax(courseWordInfoService.insertCourseWordInfo(courseWordInfo));
    }

    /**
     * 修改课程作业
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:edit')")
    @Log(title = "课程作业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseWordInfoEdit courseWordInfoEdit)
    {
        CourseWordInfo courseWordInfo = CourseWordInfoEdit.editToObj(courseWordInfoEdit);
        return toAjax(courseWordInfoService.updateCourseWordInfo(courseWordInfo));
    }

    /**
     * 删除课程作业
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordInfo:remove')")
    @Log(title = "课程作业", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseWordInfoService.deleteCourseWordInfoByIds(ids));
    }
}
