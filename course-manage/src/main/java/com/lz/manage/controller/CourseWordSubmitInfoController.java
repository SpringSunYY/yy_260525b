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
import com.lz.manage.model.domain.CourseWordSubmitInfo;
import com.lz.manage.model.vo.courseWordSubmitInfo.CourseWordSubmitInfoVo;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoQuery;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoInsert;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoEdit;
import com.lz.manage.service.ICourseWordSubmitInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 作业提交Controller
 *
 * @author YY
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/manage/courseWordSubmitInfo")
public class CourseWordSubmitInfoController extends BaseController
{
    @Resource
    private ICourseWordSubmitInfoService courseWordSubmitInfoService;

    /**
     * 查询作业提交列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseWordSubmitInfoQuery courseWordSubmitInfoQuery)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = CourseWordSubmitInfoQuery.queryToObj(courseWordSubmitInfoQuery);
        startPage();
        List<CourseWordSubmitInfo> list = courseWordSubmitInfoService.selectCourseWordSubmitInfoList(courseWordSubmitInfo);
        List<CourseWordSubmitInfoVo> listVo= list.stream().map(CourseWordSubmitInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出作业提交列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:export')")
    @Log(title = "作业提交", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseWordSubmitInfoQuery courseWordSubmitInfoQuery)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = CourseWordSubmitInfoQuery.queryToObj(courseWordSubmitInfoQuery);
        List<CourseWordSubmitInfo> list = courseWordSubmitInfoService.selectCourseWordSubmitInfoList(courseWordSubmitInfo);
        ExcelUtil<CourseWordSubmitInfo> util = new ExcelUtil<CourseWordSubmitInfo>(CourseWordSubmitInfo.class);
        util.exportExcel(response, list, "作业提交数据");
    }

    /**
     * 获取作业提交详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = courseWordSubmitInfoService.selectCourseWordSubmitInfoById(id);
        return success(CourseWordSubmitInfoVo.objToVo(courseWordSubmitInfo));
    }

    /**
     * 新增作业提交
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:add')")
    @Log(title = "作业提交", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseWordSubmitInfoInsert courseWordSubmitInfoInsert)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = CourseWordSubmitInfoInsert.insertToObj(courseWordSubmitInfoInsert);
        return toAjax(courseWordSubmitInfoService.insertCourseWordSubmitInfo(courseWordSubmitInfo));
    }

    /**
     * 修改作业提交
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:edit')")
    @Log(title = "作业提交", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseWordSubmitInfoEdit courseWordSubmitInfoEdit)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = CourseWordSubmitInfoEdit.editToObj(courseWordSubmitInfoEdit);
        return toAjax(courseWordSubmitInfoService.updateCourseWordSubmitInfo(courseWordSubmitInfo));
    }

    /**
     * 审批作业
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:auth')")
    @Log(title = "作业提交", businessType = BusinessType.UPDATE)
    @PutMapping("/auth")
    public AjaxResult auth(@RequestBody CourseWordSubmitInfoEdit courseWordSubmitInfoEdit)
    {
        CourseWordSubmitInfo courseWordSubmitInfo = CourseWordSubmitInfoEdit.editToObj(courseWordSubmitInfoEdit);
        return toAjax(courseWordSubmitInfoService.authCourseWordSubmitInfo(courseWordSubmitInfo));
    }

    /**
     * 删除作业提交
     */
    @PreAuthorize("@ss.hasPermi('manage:courseWordSubmitInfo:remove')")
    @Log(title = "作业提交", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseWordSubmitInfoService.deleteCourseWordSubmitInfoByIds(ids));
    }
}
