<template>
  <div class="app-container home">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程编号" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入课程编号"
            clearable
            style="width: 160px"
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程类型" prop="courseType">
        <el-select v-model="queryParams.courseType" style="width: 160px" placeholder="请选择课程类型" clearable>
          <el-option
              v-for="dict in manage_course_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称" prop="courseName">
        <el-input
            v-model="queryParams.courseName"
            placeholder="请输入课程名称"
            clearable
            style="width: 160px"
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="老师" prop="userId">
        <el-select
            v-model="queryParams.userId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入用户名称"
            :remote-method="remoteGetUserList"
            :loading="userLoading"
            style="width: 180px"
        >
          <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="20" class="sort-bar">
      <el-col :span="12">
        <span class="result-tip">共找到 {{ total }} 门课程</span>
      </el-col>
      <el-col :span="12" style="text-align: right;">
        <span class="sort-label">排序：</span>
        <el-button
            style="margin: 5px"
            v-for="item in sortOptions"
            :key="item.value"
            :type="currentSort === item.value ? 'primary' : 'default'"
            :underline="false"
            class="sort-btn"
            @click="handleSort(item.value)"
        >{{ item.label }}
        </el-button>
      </el-col>
    </el-row>

    <div v-loading="loading" class="course-grid">
      <el-empty v-if="!loading && courseInfoList.length === 0" description="暂无课程"/>

      <el-row v-else :gutter="20">
        <el-col
            v-for="item in courseInfoList"
            :key="item.id"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
            :xl="4"
        >
          <div class="course-card" @click="handleCardClick(item)">
            <div class="card-cover">
              <el-image
                  v-if="item.courseCover"
                  :src="baseUrl+item.courseCover"
                  fit="cover"
                  class="cover-image"
              >
                <template #error>
                  <div class="image-placeholder">
                    <el-icon :size="40">
                      <Picture/>
                    </el-icon>
                  </div>
                </template>
              </el-image>
              <div v-else class="image-placeholder">
                <el-icon :size="40">
                  <Picture/>
                </el-icon>
              </div>
              <div class="cover-overlay">
                <span class="course-type-tag">
                  <dict-tag :options="manage_course_type" :value="item.courseType"/>
                </span>
              </div>
            </div>

            <div class="card-body">
              <h3 class="course-name" :title="item.courseName">{{ item.courseName }}</h3>

              <div class="card-meta">
                <div class="meta-item">
                  <el-icon>
                    <User/>
                  </el-icon>
                  <span>{{ item.userName || '暂无老师' }}</span>
                </div>
                <div class="meta-item">
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  <span>{{ item.registerNum || 0 }} 人学习</span>
                </div>
              </div>

              <div class="card-footer">
                <div class="footer-left">
                  <el-icon>
                    <Star/>
                  </el-icon>
                  <span>{{ item.likeNum || 0 }}</span>
                </div>
                <div class="footer-right">
                  <dict-tag :options="manage_course_status" :value="item.status"/>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />
  </div>
</template>

<script setup name="Index">
import {listCourseInfo} from "@/api/manage/courseInfo";
import {allocatedUserListAll} from "@/api/system/role.js";
import {Picture, Star, User, UserFilled} from "@element-plus/icons-vue";

const {proxy} = getCurrentInstance();
const {manage_course_type, manage_course_status} = proxy.useDict('manage_course_type', 'manage_course_status');

const baseUrl = import.meta.env.VITE_APP_BASE_API;
const courseInfoList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const total = ref(0);
const daterangeCreateTime = ref([]);

const sortOptions = [
  {label: '默认排序', value: 'default'},
  {label: '注册人数', value: 'register_num'},
  {label: '点赞人数', value: 'like_num'},
  {label: '创建时间', value: 'create_time'},
];
const currentSort = ref('default');

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 12,
    id: null,
    courseType: null,
    courseName: null,
    status: '1',
    userId: null,
    params: {},
  },
  columns: [
    {key: 0, label: '课程编号', visible: true},
    {key: 1, label: '课程类型', visible: true},
    {key: 2, label: '课程名称', visible: true},
    {key: 3, label: '封面', visible: true},
    {key: 4, label: '课程描述', visible: true},
    {key: 5, label: '排序', visible: true},
    {key: 6, label: '注册人数', visible: true},
    {key: 7, label: '点赞人数', visible: true},
    {key: 8, label: '状态', visible: true},
    {key: 9, label: '老师', visible: true},
    {key: 10, label: '备注', visible: true},
    {key: 11, label: '创建人', visible: true},
    {key: 12, label: '创建时间', visible: true},
    {key: 13, label: '更新人', visible: false},
    {key: 14, label: '更新时间', visible: false},
  ],
});

const {queryParams, columns} = toRefs(data);

function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (orderByColumn.value != null && isAsc.value !== null) {
    queryParams.value.params["orderByColumn"] = orderByColumn.value;
    queryParams.value.params["isAsc"] = isAsc.value;
  }
  listCourseInfo(queryParams.value).then(response => {
    courseInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

function handleSort(sortValue) {
  currentSort.value = sortValue;
  if (sortValue === 'default') {
    orderByColumn.value = null;
    isAsc.value = null;
  } else {
    orderByColumn.value = sortValue;
    isAsc.value = false;
  }
  queryParams.value.pageNum = 1;
  getList();
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

function resetQuery() {
  daterangeCreateTime.value = [];
  queryParams.value.id = null;
  queryParams.value.courseType = null;
  queryParams.value.courseName = null;
  queryParams.value.userId = null;
  proxy.resetForm("queryRef");
  handleQuery();
}

function handleCardClick(row) {
  proxy.$tab.openPage('课程详情-' + row.courseName, '/manage/courseInfo/index/' + row.id);
}

const isAsc = ref();
const orderByColumn = ref('');

const userList = ref([]);
const userLoading = ref(false);
const userQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 2
});
const getUserList = () => {
  userLoading.value = true;
  allocatedUserListAll(userQueryParams).then(response => {
    userList.value = response.rows;
    userLoading.value = false;
  });
};
const remoteGetUserList = (query) => {
  userQueryParams.name = query;
  getUserList();
};

getUserList();
getList();
</script>

<style scoped lang="scss">
.home {
  padding: 20px;
}

.sort-bar {
  margin-bottom: 16px;
  align-items: center;
}

.result-tip {
  font-size: 14px;
  color: #909399;
  line-height: 32px;
}

.sort-label {
  font-size: 14px;
  color: #606266;
  margin-right: 8px;
  line-height: 32px;
}

.sort-btn {
  padding: 4px 12px !important;
  font-size: 13px;
  margin-left: 4px;
}

.course-grid {
  min-height: 300px;
}

.course-grid .el-row {
  display: flex;
  flex-wrap: wrap;
}

.course-grid .el-col {
  display: flex;
  margin-bottom: 20px;
}

.course-card {
  width: 100%;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #ebeef5;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
    border-color: var(--el-color-primary-light-7);
  }
}

.card-cover {
  position: relative;
  width: 100%;
  height: 160px;
  overflow: hidden;
  background: #f5f7fa;
}

.cover-image {
  width: 100%;
  height: 100%;
}

.image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
  background: #f5f7fa;
}

.cover-overlay {
  position: absolute;
  top: 8px;
  left: 8px;
  right: 8px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.course-type-tag {
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.card-body {
  padding: 14px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.course-name {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  line-height: 1.4;
}

.course-desc {
  font-size: 13px;
  color: #909399;
  margin: 0 0 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  line-height: 1.6;
  flex: 1;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #909399;

  .el-icon {
    font-size: 13px;
  }
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #f0f2f5;
}

.footer-left {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #f56c6c;
  font-weight: 500;

  .el-icon {
    font-size: 14px;
  }
}

.footer-right {
  display: flex;
  align-items: center;
}

:deep(.el-button) {
  & + .el-button {
    margin-left: 0;
  }
}
</style>
