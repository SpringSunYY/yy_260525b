<template>
  <div class="app-container course-detail">
    <div v-loading="courseLoading">
      <div v-if="courseInfo" class="detail-content">

        <!-- 顶部大图 Banner -->
        <div class="course-banner">
          <div class="banner-cover">
            <el-image
                v-if="courseInfo.courseCover"
                :src="baseUrl + courseInfo.courseCover"
                fit="cover"
                class="banner-image"
            >
              <template #error>
                <div class="banner-placeholder">
                  <el-icon :size="80">
                    <Picture/>
                  </el-icon>
                </div>
              </template>
            </el-image>
            <div v-else class="banner-placeholder">
              <el-icon :size="80">
                <Picture/>
              </el-icon>
            </div>
            <div class="banner-overlay"></div>
          </div>
          <div class="banner-info">
            <div class="banner-top">
              <dict-tag :options="manage_course_type" :value="courseInfo.courseType"/>
              <dict-tag :options="manage_course_status" :value="courseInfo.status"/>
            </div>
            <h1 class="banner-title">{{ courseInfo.courseName }}</h1>            <div class="banner-meta">
              <span class="meta-item">
                <el-icon><User/></el-icon>
                {{ courseInfo.userName || '暂无老师' }}
              </span>
              <span class="meta-divider"></span>
              <span class="meta-item">
                <el-icon><UserFilled/></el-icon>
                {{ courseInfo.registerNum || 0 }} 人已学习
              </span>
              <span class="meta-divider"></span>
              <span class="meta-item">
                <el-icon><Star/></el-icon>
                {{ courseInfo.likeNum || 0 }} 点赞
              </span>
            </div>
            <div class="banner-actions">
              <el-button
                  v-hasPermi="['manage:courseLikeInfo:add']"
                  :type="courseInfo.like ? 'danger' : 'default'"
                  :icon="courseInfo.like ? 'Star' : 'Star'"
                  round
                  @click="handleLike"
                  :loading="likeLoading"
              >
                {{ courseInfo.like ? '取消点赞' : '点赞' }}
              </el-button>
              <el-button
                  v-if="!courseInfo.register"
                  v-hasPermi="['manage:courseRegisterInfo:add']"
                  type="primary"
                  icon="Collection"
                  round
                  @click="handleRegister"
                  :loading="registerLoading"
              >
                立即报名
              </el-button>
              <el-tag v-else type="warning" effect="plain">已报名</el-tag>
            </div>
          </div>
        </div>

        <!-- 下方内容区 -->
        <div class="detail-body">
          <!-- 左侧：课程资料 -->
          <div class="main-content">
            <el-card class="materials-card" shadow="never">
              <template #header>
                <div class="section-header">
                  <span class="section-title">
                    <el-icon class="title-icon"><Document/></el-icon>
                    课程资料
                  </span>
                  <span class="section-count">{{ total }} 个</span>
                </div>
              </template>
              <div v-loading="materialsLoading">
                <el-empty v-if="!materialsLoading && materialList.length === 0" description="暂无课程资料"/>

                <div v-else class="materials-list">
                  <div
                      v-for="item in materialList"
                      :key="item.id"
                      class="material-item"
                  >
                    <div class="material-thumb">
                      <el-image
                          v-if="item.materialCover"
                          :src="baseUrl + item.materialCover"
                          fit="cover"
                          class="thumb-img"
                      >
                        <template #error>
                          <div class="thumb-placeholder">
                            <el-icon :size="24">
                              <Document/>
                            </el-icon>
                          </div>
                        </template>
                      </el-image>
                      <div v-else class="thumb-placeholder">
                        <el-icon :size="24">
                          <Document/>
                        </el-icon>
                      </div>
                    </div>
                    <div class="material-info">
                      <div class="material-name-row">
                        <span class="material-name" :title="item.material">{{ item.material }}</span>
                        <file-view :file-url="item.materialFile" v-if="item.materialFile" class="file-link"/>
                      </div>
                      <p v-if="item.materialDesc" class="material-desc" :title="item.materialDesc">
                        {{ item.materialDesc }}
                      </p>
                      <div class="material-footer-row">
                        <span class="material-teacher">
                          <el-icon><User/></el-icon>
                          {{ item.userName || '暂无老师' }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </el-card>
          </div>

          <!-- 右侧：课程信息侧栏 -->
          <div class="sidebar">
            <el-card class="info-card" shadow="never">
              <template #header>
                <div class="section-header">
                  <span class="section-title">
                    <el-icon class="title-icon"><InfoFilled/></el-icon>
                    课程信息
                  </span>
                </div>
              </template>
              <div class="info-list">
                <div class="info-item">
                  <span class="info-label">课程编号</span>
                  <span class="info-value">{{ courseInfo.id }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">授课老师</span>
                  <span class="info-value">{{ courseInfo.userName || '-' }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">注册人数</span>
                  <span class="info-value">{{ courseInfo.registerNum || 0 }} 人</span>
                </div>
                <div class="info-item">
                  <span class="info-label">点赞人数</span>
                  <span class="info-value">{{ courseInfo.likeNum || 0 }} 人</span>
                </div>
                <div class="info-item">
                  <span class="info-label">排序值</span>
                  <span class="info-value">{{ courseInfo.orderNum ?? '-' }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">创建时间</span>
                  <span class="info-value">{{
                      courseInfo.createTime ? parseTime(courseInfo.createTime, '{y}-{m}-{d}') : '-'
                    }}</span>
                </div>
              </div>
              <div v-if="courseInfo.courseDesc" class="desc-block">
                <span class="desc-label">课程描述</span>
                <div class="desc-content" v-html="courseInfo.courseDesc"></div>
              </div>
              <div v-if="courseInfo.remark" class="remark-block">
                <span class="remark-label">备注</span>
                <p class="remark-text">{{ courseInfo.remark }}</p>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup name="CourseDetail">
import {getCourseInfo} from "@/api/manage/courseInfo";
import {listCourseMaterialInfo} from "@/api/manage/courseMaterialInfo";
import {addCourseLikeInfo} from "@/api/manage/courseLikeInfo";
import {addCourseRegisterInfo} from "@/api/manage/courseRegisterInfo";
import {Document, InfoFilled, Picture, Star, User, UserFilled} from "@element-plus/icons-vue";
import useUserStore from "@/store/modules/user";

const route = useRoute();
const {proxy} = getCurrentInstance();
const {manage_course_type, manage_course_status} = proxy.useDict('manage_course_type', 'manage_course_status');

const baseUrl = import.meta.env.VITE_APP_BASE_API;
const userStore = useUserStore();

const courseInfo = ref(null);
const courseLoading = ref(true);
const likeLoading = ref(false);
const registerLoading = ref(false);

const materialList = ref([]);
const materialsLoading = ref(false);
const total = ref(0);

const materialQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  courseId: null,
});

function parseTime(time, fmt) {
  return proxy.parseTime(time, fmt);
}

function getCourseDetail() {
  const id = route.params && route.params.id;
  if (!id) {
    courseLoading.value = false;
    return;
  }
  courseLoading.value = true;
  getCourseInfo(id).then(response => {
    courseInfo.value = response.data;
    materialQueryParams.courseId = response.data.id;
    courseLoading.value = false;
    getMaterials();
  });
}

function getMaterials() {
  if (!materialQueryParams.courseId) return;
  materialsLoading.value = true;
  listCourseMaterialInfo(materialQueryParams).then(response => {
    materialList.value = response.rows;
    total.value = response.total;
    materialsLoading.value = false;
  });
}

function handleLike() {
  if (!courseInfo.value) return;
  likeLoading.value = true;
  addCourseLikeInfo({
    courseId: courseInfo.value.id,
    teacherId: courseInfo.value.userId,
    userId: userStore.id,
  }).then(() => {
    const current = courseInfo.value.like;
    courseInfo.value.like = !current;
    courseInfo.value.likeNum = Number(courseInfo.value.likeNum || 0) + (current ? -1 : 1);
    proxy.$modal.msgSuccess(current ? '取消点赞成功' : '点赞成功');
  }).catch(() => {
  }).finally(() => {
    likeLoading.value = false;
  });
}

function handleRegister() {
  if (!courseInfo.value) return;
  registerLoading.value = true;
  addCourseRegisterInfo({
    courseId: courseInfo.value.id,
    userId: userStore.id,
  }).then(response => {
    courseInfo.value.register = true;
    courseInfo.value.registerId = response.data?.id;
    courseInfo.value.registerNum = Number(courseInfo.value.registerNum || 0) + 1;
    proxy.$modal.msgSuccess('报名成功');
  }).catch(() => {
  }).finally(() => {
    registerLoading.value = false;
  });
}

onMounted(() => {
  getCourseDetail();
});
</script>

<style scoped lang="scss">
.course-detail {
  padding: 0;
  background: #f5f7fa;
}

.detail-content {
  display: flex;
  flex-direction: column;
}

/* Banner 区域 */
.course-banner {
  position: relative;
  height: 320px;
  overflow: hidden;
}

.banner-cover {
  position: absolute;
  inset: 0;
}

.banner-image {
  width: 100%;
  height: 100%;
}

.banner-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: rgba(255, 255, 255, 0.6);
}

.banner-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.7) 0%, rgba(0, 0, 0, 0.3) 50%, rgba(0, 0, 0, 0.1) 100%);
}

.banner-info {
  position: relative;
  z-index: 1;
  height: 100%;
  padding: 24px 32px;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.banner-top {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.banner-title {
  font-size: 28px;
  font-weight: 700;
  color: #fff;
  margin: 0 0 10px;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  line-height: 1.3;
}

.banner-desc {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.85);
  margin: 0 0 16px;
  line-height: 1.6;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  /* autoprefixer: ignore next */
  -webkit-line-clamp: 2;
  line-clamp: 2;
}

.banner-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);

  .el-icon {
    font-size: 14px;
  }
}

.meta-divider {
  width: 1px;
  height: 14px;
  background: rgba(255, 255, 255, 0.3);
}

.banner-actions {
  display: flex;
  gap: 12px;
}

/* 下方内容区 */
.detail-body {
  display: flex;
  gap: 20px;
  padding: 20px;
  align-items: flex-start;
}

.main-content {
  flex: 1;
  min-width: 0;
}

.sidebar {
  width: 280px;
  flex-shrink: 0;
}

/* 通用卡片样式 */
:deep(.el-card) {
  border: none;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  overflow: hidden;
}

:deep(.el-card__header) {
  padding: 16px 20px;
  background: #fff;
  border-bottom: 1px solid #f0f2f5;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 15px;
  font-weight: 600;
  color: #303133;
}

.title-icon {
  font-size: 16px;
  color: var(--el-color-primary);
}

.section-count {
  font-size: 13px;
  color: #909399;
}

/* 资料卡片 */
.materials-card :deep(.el-card__body) {
  padding: 0;
}

.materials-list {
  display: flex;
  flex-direction: column;
}

.material-item {
  display: flex;
  gap: 16px;
  padding: 16px 20px;
  border-bottom: 1px solid #f5f7fa;
  transition: background 0.2s;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background: #fafafa;
  }
}

.material-thumb {
  flex-shrink: 0;
  width: 80px;
  height: 60px;
  border-radius: 6px;
  overflow: hidden;
  background: #f5f7fa;
}

.thumb-img {
  width: 100%;
  height: 100%;
}

.thumb-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
  background: #f5f7fa;
}

.material-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.material-name-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  margin-bottom: 4px;
}

.material-name {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
}

.file-link {
  flex-shrink: 0;
}

.material-desc {
  font-size: 12px;
  color: #909399;
  margin: 0 0 6px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.material-footer-row {
  display: flex;
  align-items: center;
}

.material-teacher {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #909399;

  .el-icon {
    font-size: 12px;
  }
}

/* 侧栏信息卡片 */
.info-card :deep(.el-card__body) {
  padding: 16px 20px;
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: 0;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f5f7fa;

  &:last-child {
    border-bottom: none;
  }
}

.info-label {
  font-size: 13px;
  color: #909399;
}

.info-value {
  font-size: 13px;
  color: #303133;
  font-weight: 500;
}

.desc-block {
  margin-top: 14px;
  padding-top: 14px;
  border-top: 1px solid #f0f2f5;
}

.desc-label {
  font-size: 13px;
  color: #909399;
  display: block;
  margin-bottom: 8px;
}

.desc-content {
  font-size: 13px;
  color: #606266;
  line-height: 1.8;
  overflow: hidden;

  :deep(p) {
    margin: 0 0 6px;
  }

  :deep(img) {
    max-width: 100%;
    border-radius: 4px;
  }
}

.remark-block {
  margin-top: 14px;
  padding-top: 14px;
  border-top: 1px solid #f0f2f5;
}

.remark-label {
  font-size: 13px;
  color: #909399;
  display: block;
  margin-bottom: 6px;
}

.remark-text {
  font-size: 13px;
  color: #606266;
  margin: 0;
  line-height: 1.6;
}

@media (max-width: 1024px) {
  .detail-body {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .course-banner {
    height: 240px;
  }

  .banner-title {
    font-size: 22px;
  }

  .banner-info {
    padding: 16px 20px;
  }
}
</style>
