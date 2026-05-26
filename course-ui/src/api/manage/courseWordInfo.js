import request from '@/utils/request'

// 查询课程作业列表
export function listCourseWordInfo(query) {
  return request({
    url: '/manage/courseWordInfo/list',
    method: 'get',
    params: query
  })
}

// 查询课程作业详细
export function getCourseWordInfo(id) {
  return request({
    url: '/manage/courseWordInfo/' + id,
    method: 'get'
  })
}

// 新增课程作业
export function addCourseWordInfo(data) {
  return request({
    url: '/manage/courseWordInfo',
    method: 'post',
    data: data
  })
}

// 修改课程作业
export function updateCourseWordInfo(data) {
  return request({
    url: '/manage/courseWordInfo',
    method: 'put',
    data: data
  })
}

// 删除课程作业
export function delCourseWordInfo(id) {
  return request({
    url: '/manage/courseWordInfo/' + id,
    method: 'delete'
  })
}
