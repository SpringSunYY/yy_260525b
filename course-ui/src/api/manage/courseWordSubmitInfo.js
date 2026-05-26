import request from '@/utils/request'

// 查询作业提交列表
export function listCourseWordSubmitInfo(query) {
  return request({
    url: '/manage/courseWordSubmitInfo/list',
    method: 'get',
    params: query
  })
}

// 查询作业提交详细
export function getCourseWordSubmitInfo(id) {
  return request({
    url: '/manage/courseWordSubmitInfo/' + id,
    method: 'get'
  })
}

// 新增作业提交
export function addCourseWordSubmitInfo(data) {
  return request({
    url: '/manage/courseWordSubmitInfo',
    method: 'post',
    data: data
  })
}

// 修改作业提交
export function updateCourseWordSubmitInfo(data) {
  return request({
    url: '/manage/courseWordSubmitInfo',
    method: 'put',
    data: data
  })
}

// 删除作业提交
export function delCourseWordSubmitInfo(id) {
  return request({
    url: '/manage/courseWordSubmitInfo/' + id,
    method: 'delete'
  })
}
