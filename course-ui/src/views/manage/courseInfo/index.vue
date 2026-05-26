<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程编号" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入课程编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程类型" prop="courseType">
        <el-select v-model="queryParams.courseType" style="width: 200px" placeholder="请选择课程类型" clearable>
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
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" style="width: 200px" placeholder="请选择状态" clearable>
          <el-option
              v-for="dict in manage_course_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
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
            style="width: 100%"
        >
          <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
          />
        </el-select>
      </el-form-item>
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
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['manage:courseInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:courseInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:courseInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:courseInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table ref="tableRef" v-loading="loading" :data="courseInfoList" @selection-change="handleSelectionChange"
              @sort-change="customSort">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="课程编号" align="center" prop="id" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="课程类型" align="center" prop="courseType" v-if="columns[1].visible">
        <template #default="scope">
          <dict-tag :options="manage_course_type" :value="scope.row.courseType"/>
        </template>
      </el-table-column>
      <el-table-column label="课程名称" align="center" prop="courseName" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="封面" align="center" prop="courseCover" width="100" v-if="columns[3].visible">
        <template #default="scope">
          <image-preview :src="scope.row.courseCover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="课程描述" align="center" prop="courseDesc" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="排序" align="center" prop="orderNum" v-if="columns[5].visible"
                       column-key="order_num" sortable="custom"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="注册人数" align="center" prop="registerNum" v-if="columns[6].visible"
                       column-key="register_num" sortable="custom"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="点赞人数" align="center" prop="likeNum" v-if="columns[7].visible"
                       column-key="like_num" sortable="custom"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="status" v-if="columns[8].visible">
        <template #default="scope">
          <dict-tag :options="manage_course_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="老师" align="center" prop="userName" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" column-key="create_time"
                       sortable="custom" v-if="columns[12].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:courseInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:courseInfo:remove']">删除
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

    <!-- 添加或修改课程信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="form.courseType" placeholder="请选择课程类型">
            <el-option
                v-for="dict in manage_course_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称"/>
        </el-form-item>
        <el-form-item label="封面" prop="courseCover">
          <image-upload :limit="1" v-model="form.courseCover"/>
        </el-form-item>
        <el-form-item label="课程描述" prop="courseDesc">
          <editor :min-height="192" v-model="form.courseDesc" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input-number style="width: 100%" :max="10" :min="0" v-model="form.orderNum" placeholder="请输入排序"/>
        </el-form-item>
        <!--        <el-form-item label="注册人数" prop="registerNum">-->
        <!--          <el-input v-model="form.registerNum" placeholder="请输入注册人数"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="点赞人数" prop="likeNum">-->
        <!--          <el-input v-model="form.likeNum" placeholder="请输入点赞人数"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in manage_course_status"
                :key="dict.value"
                :value="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="老师" prop="userId">
          <el-select
              v-model="form.userId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetUserList"
              :loading="userLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.userName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
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
  </div>
</template>

<script setup name="CourseInfo">
import {addCourseInfo, delCourseInfo, getCourseInfo, listCourseInfo, updateCourseInfo} from "@/api/manage/courseInfo";
import {allocatedUserListAll} from "@/api/system/role.js";

const {proxy} = getCurrentInstance();
const {manage_course_type, manage_course_status} = proxy.useDict('manage_course_type', 'manage_course_status');

const courseInfoList = ref([]);
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
  exportUrl: 'manage/courseInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    courseType: null,
    courseName: null,
    status: null,
    userId: null,
    createTime: null,
  },
  rules: {
    courseType: [
      {required: true, message: "课程类型不能为空", trigger: "change"}
    ],
    courseName: [
      {required: true, message: "课程名称不能为空", trigger: "blur"}
    ],
    orderNum: [
      {required: true, message: "排序不能为空", trigger: "blur"}
    ],
    registerNum: [
      {required: true, message: "注册人数不能为空", trigger: "blur"}
    ],
    likeNum: [
      {required: true, message: "点赞人数不能为空", trigger: "blur"}
    ],
    status: [
      {required: true, message: "状态不能为空", trigger: "change"}
    ],
    userId: [
      {required: true, message: "老师不能为空", trigger: "blur"}
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

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询课程信息列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (orderByColumn.value != null && isAsc.value !== null) {
    queryParams.value.params["orderByColumn"] = orderByColumn.value;
    queryParams.value.params["isAsc"] = isAsc.value;
  }
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listCourseInfo(queryParams.value).then(response => {
    courseInfoList.value = response.rows;
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
    courseType: null,
    courseName: null,
    courseCover: null,
    courseDesc: null,
    orderNum: null,
    registerNum: null,
    likeNum: null,
    status: null,
    userId: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  };
  proxy.resetForm("courseInfoRef");
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
  proxy.$refs.tableRef.clearSort();
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
  title.value = "添加课程信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCourseInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课程信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourseInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourseInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除课程信息编号为"' + _ids + '"的数据项？').then(function () {
    return delCourseInfo(_ids);
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
  }, `courseInfo_${new Date().getTime()}.xlsx`)
}

const isAsc = ref();
const orderByColumn = ref('');

//自定义排序
function customSort({column, prop, order}) {
  if (prop !== undefined && prop !== '' && order !== null && order !== '') {
    orderByColumn.value = prop;
    isAsc.value = order === "ascending";
  } else {
    orderByColumn.value = null;
    isAsc.value = null;
  }
  queryParams.value.pageNum = 1;
  getList();
}

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
}

getUserList()

getList();
</script>
