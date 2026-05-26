<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程" prop="courseId">
        <el-select
            v-model="queryParams.courseId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入课程名称"
            :remote-method="remoteGetCourseList"
            :loading="courseLoading"
            style="width: 100%"
        >
          <el-option
              v-for="item in courseList"
              :key="item.id"
              :label="item.courseName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="作业" prop="wordId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.wordId"-->
      <!--            placeholder="请输入作业"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" style="width: 200px" placeholder="请选择状态" clearable>
          <el-option
              v-for="dict in course_word_submit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="批阅状态" prop="reviewStatus">
        <el-select v-model="queryParams.reviewStatus" style="width: 200px" placeholder="请选择批阅状态" clearable>
          <el-option
              v-for="dict in course_word_submit_review_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="老师" prop="teacherId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.teacherId"-->
      <!--            placeholder="请输入老师"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="学生" prop="userId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.userId"-->
      <!--            placeholder="请输入学生"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker
            v-model="daterangeCreateTime"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <!--        <el-button-->
        <!--            type="primary"-->
        <!--            plain-->
        <!--            icon="Plus"-->
        <!--            @click="handleAdd"-->
        <!--            v-hasPermi="['manage:courseWordSubmitInfo:add']"-->
        <!--        >新增-->
        <!--        </el-button>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-button-->
        <!--            type="success"-->
        <!--            plain-->
        <!--            icon="Edit"-->
        <!--            :disabled="single"-->
        <!--            @click="handleUpdate"-->
        <!--            v-hasPermi="['manage:courseWordSubmitInfo:edit']"-->
        <!--        >修改-->
        <!--        </el-button>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:courseWordSubmitInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:courseWordSubmitInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseWordSubmitInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="课程" align="center" prop="courseName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="作业" align="center" prop="wordName" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="status" v-if="columns[3].visible">
        <template #default="scope">
          <dict-tag :options="course_word_submit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="提交内容" align="center" prop="submitContent" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="提交文件" align="center" prop="submitFile" width="100" v-if="columns[5].visible">
        <template #default="scope">
          <file-view :file-url="scope.row.submitFile"/>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" prop="submitTime" width="180" v-if="columns[6].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="批阅状态" align="center" prop="reviewStatus" v-if="columns[7].visible">
        <template #default="scope">
          <dict-tag :options="course_word_submit_review_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="分数" align="center" prop="score" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="批阅时间" align="center" prop="reviewTime" width="180" v-if="columns[9].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="老师" align="center" prop="teacherName" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="学生" align="center" prop="userName" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[16].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"
                       width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:courseWordSubmitInfo:edit']">提交
          </el-button>
          <el-button link type="primary" icon="Auth" @click="handleAuth(scope.row)"
                     v-hasPermi="['manage:courseWordSubmitInfo:auth']">审批
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:courseWordSubmitInfo:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改作业提交对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseWordSubmitInfoRef" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="课程" prop="courseId">-->
        <!--          <el-input v-model="form.courseId" placeholder="请输入课程"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="作业" prop="wordId">-->
        <!--          <el-input v-model="form.wordId" placeholder="请输入作业"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in course_word_submit_status"
                :key="dict.value"
                :value="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="提交内容">
          <editor v-model="form.submitContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="提交文件" prop="submitFile">
          <file-upload v-model="form.submitFile"/>
        </el-form-item>
        <!--        <el-form-item label="提交时间" prop="submitTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.submitTime"-->
        <!--                          type="date"-->
        <!--                          value-format="YYYY-MM-DD"-->
        <!--                          placeholder="请选择提交时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="批阅状态" prop="reviewStatus">-->
        <!--          <el-radio-group v-model="form.reviewStatus">-->
        <!--            <el-radio-->
        <!--                v-for="dict in course_word_submit_review_status"-->
        <!--                :key="dict.value"-->
        <!--                :value="dict.value"-->
        <!--            >{{ dict.label }}-->
        <!--            </el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="分数" prop="score">-->
        <!--          <el-input v-model="form.score" placeholder="请输入分数"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="批阅时间" prop="reviewTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.reviewTime"-->
        <!--                          type="date"-->
        <!--                          value-format="YYYY-MM-DD"-->
        <!--                          placeholder="请选择批阅时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="老师" prop="teacherId">-->
        <!--          <el-input v-model="form.teacherId" placeholder="请输入老师"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="学生" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入学生"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 添加或修改作业提交对话框 -->
    <el-dialog :title="title" v-model="openAuth" width="500px" append-to-body>
      <el-form ref="courseWordSubmitInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="批阅状态" prop="reviewStatus">
          <el-radio-group v-model="form.reviewStatus">
            <el-radio
                v-for="dict in course_word_submit_review_status"
                :key="dict.value"
                :value="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input-number style="width: 100%" :min="0" :max="100" :precision="2" v-model="form.score" placeholder="请输入分数"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFormAuth">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="CourseWordSubmitInfo">
import {
  addCourseWordSubmitInfo, authCourseWordSubmitInfo,
  delCourseWordSubmitInfo,
  getCourseWordSubmitInfo,
  listCourseWordSubmitInfo,
  updateCourseWordSubmitInfo
} from "@/api/manage/courseWordSubmitInfo";
import {listCourseInfo} from "@/api/manage/courseInfo.js";

const {proxy} = getCurrentInstance();
const {
  course_word_submit_status,
  course_word_submit_review_status
} = proxy.useDict('course_word_submit_status', 'course_word_submit_review_status');

const courseWordSubmitInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/courseWordSubmitInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    courseId: null,
    wordId: null,
    status: null,
    reviewStatus: null,
    teacherId: null,
    userId: null,
    createTime: null,
  },
  rules: {
    courseId: [
      {required: true, message: "课程不能为空", trigger: "blur"}
    ],
    wordId: [
      {required: true, message: "作业不能为空", trigger: "blur"}
    ],
    status: [
      {required: true, message: "状态不能为空", trigger: "change"}
    ],
    reviewStatus: [
      {required: true, message: "批阅状态不能为空", trigger: "change"}
    ],
    score: [
      {required: true, message: "分数不能为空", trigger: "blur"}
    ],
    teacherId: [
      {required: true, message: "老师不能为空", trigger: "blur"}
    ],
    userId: [
      {required: true, message: "学生不能为空", trigger: "blur"}
    ],
    createBy: [
      {required: true, message: "创建人不能为空", trigger: "blur"}
    ],
    createTime: [
      {required: true, message: "创建时间不能为空", trigger: "blur"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '课程', visible: true},
    {key: 2, label: '作业', visible: true},
    {key: 3, label: '状态', visible: true},
    {key: 4, label: '提交内容', visible: true},
    {key: 5, label: '提交文件', visible: true},
    {key: 6, label: '提交时间', visible: true},
    {key: 7, label: '批阅状态', visible: true},
    {key: 8, label: '分数', visible: true},
    {key: 9, label: '批阅时间', visible: true},
    {key: 10, label: '老师', visible: true},
    {key: 11, label: '学生', visible: true},
    {key: 12, label: '备注', visible: true},
    {key: 13, label: '创建人', visible: true},
    {key: 14, label: '创建时间', visible: true},
    {key: 15, label: '更新人', visible: false},
    {key: 16, label: '更新时间', visible: false},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询作业提交列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listCourseWordSubmitInfo(queryParams.value).then(response => {
    courseWordSubmitInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  openAuth.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    courseId: null,
    wordId: null,
    status: null,
    submitContent: null,
    submitFile: null,
    submitTime: null,
    reviewStatus: null,
    score: null,
    reviewTime: null,
    teacherId: null,
    userId: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  };
  proxy.resetForm("courseWordSubmitInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeCreateTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加作业提交";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseWordSubmitInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改作业提交";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseWordSubmitInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourseWordSubmitInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourseWordSubmitInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除作业提交编号为"' + _ids + '"的数据项？').then(function () {
    return delCourseWordSubmitInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(exportUrl.value, {
    ...queryParams.value
  }, `courseWordSubmitInfo_${new Date().getTime()}.xlsx`)
}

const courseList = ref([]);
const courseLoading = ref(false);
const courseQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  courseName: null,
  courseStatus: '1'
});
const getCourseList = () => {
  courseLoading.value = true;
  listCourseInfo(courseQueryParams).then(response => {
    courseList.value = response.rows;
    courseLoading.value = false;
  });
};
const remoteGetCourseList = (query) => {
  courseQueryParams.name = query;
  getCourseList();
}

getCourseList()

const openAuth = ref(false);
const handleAuth = (row) => {
  getCourseWordSubmitInfo(row.id).then(response => {
    form.value = response.data;
    openAuth.value = true;
    this.title = "审批作业";
  });
}

const submitFormAuth = () => {
  proxy.$refs["courseWordSubmitInfoRef"].validate(valid => {
    if (!valid) return
    authCourseWordSubmitInfo(form.value).then(response => {
      proxy.$modal.msgSuccess("审批成功");
      openAuth.value = false;
      getList();
    });
  });
}

getList();
</script>
