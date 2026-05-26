import request from '@/utils/request'

// 查询课程注册列表
export function listCourseRegisterInfo(query) {
  return request({
    url: '/manage/courseRegisterInfo/list',
    method: 'get',
    params: query
  })
}

// 查询课程注册详细
export function getCourseRegisterInfo(id) {
  return request({
    url: '/manage/courseRegisterInfo/' + id,
    method: 'get'
  })
}

// 新增课程注册
export function addCourseRegisterInfo(data) {
  return request({
    url: '/manage/courseRegisterInfo',
    method: 'post',
    data: data
  })
}

// 修改课程注册
export function updateCourseRegisterInfo(data) {
  return request({
    url: '/manage/courseRegisterInfo',
    method: 'put',
    data: data
  })
}

// 删除课程注册
export function delCourseRegisterInfo(id) {
  return request({
    url: '/manage/courseRegisterInfo/' + id,
    method: 'delete'
  })
}
