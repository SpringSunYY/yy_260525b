import request from '@/utils/request'

// 查询课程点赞列表
export function listCourseLikeInfo(query) {
  return request({
    url: '/manage/courseLikeInfo/list',
    method: 'get',
    params: query
  })
}

// 查询课程点赞详细
export function getCourseLikeInfo(id) {
  return request({
    url: '/manage/courseLikeInfo/' + id,
    method: 'get'
  })
}

// 新增课程点赞
export function addCourseLikeInfo(data) {
  return request({
    url: '/manage/courseLikeInfo',
    method: 'post',
    data: data
  })
}

// 修改课程点赞
export function updateCourseLikeInfo(data) {
  return request({
    url: '/manage/courseLikeInfo',
    method: 'put',
    data: data
  })
}

// 删除课程点赞
export function delCourseLikeInfo(id) {
  return request({
    url: '/manage/courseLikeInfo/' + id,
    method: 'delete'
  })
}
