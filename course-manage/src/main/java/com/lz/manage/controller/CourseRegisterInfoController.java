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
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.model.vo.courseRegisterInfo.CourseRegisterInfoVo;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoQuery;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoInsert;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoEdit;
import com.lz.manage.service.ICourseRegisterInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 课程注册Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseRegisterInfo")
public class CourseRegisterInfoController extends BaseController
{
    @Resource
    private ICourseRegisterInfoService courseRegisterInfoService;

    /**
     * 查询课程注册列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseRegisterInfoQuery courseRegisterInfoQuery)
    {
        CourseRegisterInfo courseRegisterInfo = CourseRegisterInfoQuery.queryToObj(courseRegisterInfoQuery);
        startPage();
        List<CourseRegisterInfo> list = courseRegisterInfoService.selectCourseRegisterInfoList(courseRegisterInfo);
        List<CourseRegisterInfoVo> listVo= list.stream().map(CourseRegisterInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出课程注册列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:export')")
    @Log(title = "课程注册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseRegisterInfoQuery courseRegisterInfoQuery)
    {
        CourseRegisterInfo courseRegisterInfo = CourseRegisterInfoQuery.queryToObj(courseRegisterInfoQuery);
        List<CourseRegisterInfo> list = courseRegisterInfoService.selectCourseRegisterInfoList(courseRegisterInfo);
        ExcelUtil<CourseRegisterInfo> util = new ExcelUtil<CourseRegisterInfo>(CourseRegisterInfo.class);
        util.exportExcel(response, list, "课程注册数据");
    }

    /**
     * 获取课程注册详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseRegisterInfo courseRegisterInfo = courseRegisterInfoService.selectCourseRegisterInfoById(id);
        return success(CourseRegisterInfoVo.objToVo(courseRegisterInfo));
    }

    /**
     * 新增课程注册
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:add')")
    @Log(title = "课程注册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseRegisterInfoInsert courseRegisterInfoInsert)
    {
        CourseRegisterInfo courseRegisterInfo = CourseRegisterInfoInsert.insertToObj(courseRegisterInfoInsert);
        return toAjax(courseRegisterInfoService.insertCourseRegisterInfo(courseRegisterInfo));
    }

    /**
     * 修改课程注册
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:edit')")
    @Log(title = "课程注册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseRegisterInfoEdit courseRegisterInfoEdit)
    {
        CourseRegisterInfo courseRegisterInfo = CourseRegisterInfoEdit.editToObj(courseRegisterInfoEdit);
        return toAjax(courseRegisterInfoService.updateCourseRegisterInfo(courseRegisterInfo));
    }

    /**
     * 删除课程注册
     */
    @PreAuthorize("@ss.hasPermi('manage:courseRegisterInfo:remove')")
    @Log(title = "课程注册", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseRegisterInfoService.deleteCourseRegisterInfoByIds(ids));
    }
}
