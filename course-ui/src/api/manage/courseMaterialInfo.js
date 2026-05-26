import request from '@/utils/request'

// 查询课程资料列表
export function listCourseMaterialInfo(query) {
  return request({
    url: '/manage/courseMaterialInfo/list',
    method: 'get',
    params: query
  })
}

// 查询课程资料详细
export function getCourseMaterialInfo(id) {
  return request({
    url: '/manage/courseMaterialInfo/' + id,
    method: 'get'
  })
}

// 新增课程资料
export function addCourseMaterialInfo(data) {
  return request({
    url: '/manage/courseMaterialInfo',
    method: 'post',
    data: data
  })
}

// 修改课程资料
export function updateCourseMaterialInfo(data) {
  return request({
    url: '/manage/courseMaterialInfo',
    method: 'put',
    data: data
  })
}

// 删除课程资料
export function delCourseMaterialInfo(id) {
  return request({
    url: '/manage/courseMaterialInfo/' + id,
    method: 'delete'
  })
}
