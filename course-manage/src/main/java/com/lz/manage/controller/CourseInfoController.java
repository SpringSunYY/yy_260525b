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
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.vo.courseInfo.CourseInfoVo;
import com.lz.manage.model.dto.courseInfo.CourseInfoQuery;
import com.lz.manage.model.dto.courseInfo.CourseInfoInsert;
import com.lz.manage.model.dto.courseInfo.CourseInfoEdit;
import com.lz.manage.service.ICourseInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 课程信息Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseInfo")
public class CourseInfoController extends BaseController
{
    @Resource
    private ICourseInfoService courseInfoService;

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseInfoQuery courseInfoQuery)
    {
        CourseInfo courseInfo = CourseInfoQuery.queryToObj(courseInfoQuery);
        startPage();
        List<CourseInfo> list = courseInfoService.selectCourseInfoList(courseInfo);
        List<CourseInfoVo> listVo= list.stream().map(CourseInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:export')")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseInfoQuery courseInfoQuery)
    {
        CourseInfo courseInfo = CourseInfoQuery.queryToObj(courseInfoQuery);
        List<CourseInfo> list = courseInfoService.selectCourseInfoList(courseInfo);
        ExcelUtil<CourseInfo> util = new ExcelUtil<CourseInfo>(CourseInfo.class);
        util.exportExcel(response, list, "课程信息数据");
    }

    /**
     * 获取课程信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(id);
        return success(CourseInfoVo.objToVo(courseInfo));
    }

    /**
     * 新增课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:add')")
    @Log(title = "课程信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseInfoInsert courseInfoInsert)
    {
        CourseInfo courseInfo = CourseInfoInsert.insertToObj(courseInfoInsert);
        return toAjax(courseInfoService.insertCourseInfo(courseInfo));
    }

    /**
     * 修改课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:edit')")
    @Log(title = "课程信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseInfoEdit courseInfoEdit)
    {
        CourseInfo courseInfo = CourseInfoEdit.editToObj(courseInfoEdit);
        return toAjax(courseInfoService.updateCourseInfo(courseInfo));
    }

    /**
     * 删除课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseInfo:remove')")
    @Log(title = "课程信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseInfoService.deleteCourseInfoByIds(ids));
    }
}
