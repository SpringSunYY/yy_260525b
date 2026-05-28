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
<!--      <el-form-item label="课程" prop="courseId">-->
<!--        <el-input-->
<!--            v-model="queryParams.courseId"-->
<!--            placeholder="请输入课程"-->
<!--            clearable-->
<!--            @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="primary"-->
<!--            plain-->
<!--            icon="Plus"-->
<!--            @click="handleAdd"-->
<!--            v-hasPermi="['manage:courseLikeInfo:add']"-->
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
<!--            v-hasPermi="['manage:courseLikeInfo:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:courseLikeInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:courseLikeInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseLikeInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="课程" align="center" prop="courseName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="老师" align="center" prop="teacherName" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="学生" align="center" prop="userName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[4].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
<!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"-->
<!--                     v-hasPermi="['manage:courseLikeInfo:edit']">修改-->
<!--          </el-button>-->
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:courseLikeInfo:remove']">删除
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

    <!-- 添加或修改课程点赞对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseLikeInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程" prop="courseId">
          <el-input v-model="form.courseId" placeholder="请输入课程"/>
        </el-form-item>
        <el-form-item label="老师" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入老师"/>
        </el-form-item>
        <el-form-item label="学生" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入学生"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="CourseLikeInfo">
import {
  addCourseLikeInfo,
  delCourseLikeInfo,
  getCourseLikeInfo,
  listCourseLikeInfo,
  updateCourseLikeInfo
} from "@/api/manage/courseLikeInfo";

const {proxy} = getCurrentInstance();

const courseLikeInfoList = ref([]);
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
  exportUrl: 'manage/courseLikeInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    courseId: null,
    teacherId: null,
    userId: null,
    createTime: null
  },
  rules: {
    courseId: [
      {required: true, message: "课程不能为空", trigger: "blur"}
    ],
    teacherId: [
      {required: true, message: "老师不能为空", trigger: "blur"}
    ],
    userId: [
      {required: true, message: "学生不能为空", trigger: "blur"}
    ],
    createTime: [
      {required: true, message: "创建时间不能为空", trigger: "blur"}
    ]
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '课程', visible: true},
    {key: 2, label: '老师', visible: true},
    {key: 3, label: '学生', visible: true},
    {key: 4, label: '创建时间', visible: true},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询课程点赞列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listCourseLikeInfo(queryParams.value).then(response => {
    courseLikeInfoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    courseId: null,
    teacherId: null,
    userId: null,
    createTime: null
  };
  proxy.resetForm("courseLikeInfoRef");
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
  title.value = "添加课程点赞";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseLikeInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课程点赞";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseLikeInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourseLikeInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourseLikeInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除课程点赞编号为"' + _ids + '"的数据项？').then(function () {
    return delCourseLikeInfo(_ids);
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
  }, `courseLikeInfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
