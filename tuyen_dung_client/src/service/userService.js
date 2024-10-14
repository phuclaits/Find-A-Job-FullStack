import axios from "../axios";

const getListPostService = (data) => {
  if (!data?.search) {
    data.search = "";
  }
  if (data.isHot === 1) {
    return axios.get(
      `/public/get-filter-post?limit=${data.limit}&offset=${data.offset}&categoryJobCode=${data.categoryJobCode}&addressCode=${data.addressCode}&salaryJobCode=${data.salaryJobCode}&categoryJoblevelCode=${data.categoryJoblevelCode}&categoryWorktypeCode=${data.categoryWorktypeCode}&experienceJobCode=${data.experienceJobCode}&isHot=${data.isHot}&search=${data.search}`
    );
  }
  return axios.get(
    `/public/get-filter-post?limit=${data.limit}&offset=${data.offset}&categoryJobCode=${data.categoryJobCode}&addressCode=${data.addressCode}&salaryJobCode=${data.salaryJobCode}&categoryJoblevelCode=${data.categoryJoblevelCode}&categoryWorktypeCode=${data.categoryWorktypeCode}&experienceJobCode=${data.experienceJobCode}&search=${data.search}`
  );
};
const getListJobTypeAndCountPost = (data) => {};

//===============ALL CODE========================//
const getAllCodeService = (type) => {
  return axios.get(`/public/get-all-code?type=${type}`);
};

const getDetailPostByIdService = (id) => {
  return axios.get(`/public/get-detail-post-by-id?id=${id}`);
};

const getListCompany = (data) => {
  return axios.get(`/public/get-list-company?limit=${data.limit}&offset=${data.offset}&search=${data.search}`)

}

const getDetailCompanyById = (id) => {
  return axios.get(`/public/admin/company/get_company/${id}`)

}

const handleLoginService = (data) => {
  return axios.post(`http://localhost:8080/auth/login`, data);
};

const createNewUser = (data) => {
  return axios.post(`http://localhost:8080/auth/register`, data);
};
const getDetailUserById = (data) => {
  return axios.get(`http://localhost:8080/public/get-users/${data}`);
};

const UpdateUserService = (data) => {
  return axios.put(`/public/update`, data);
};

const UpdateUserSettingService = (data) => {
  return axios.put(`/public/set-user-setting`, data);
};

const getAllSkillByJobCode = (categoryJobCode) => {
  return axios.get(
    `/public/skill/get-all-skill-by-job-code?categoryJobCode=${categoryJobCode}`
  );
};

const getDetailCompanyByUserId = (userId, companyId) => {
  return axios.get(
    `/public/admin/api/get-detail-company-by-userId?userId=${userId}&companyId=${companyId}`
  );
};
const createCompanyService = async (formData) => {
  try {
    console.log(formData);
    let response = await axios.post(
      "http://localhost:8080/public/admin/create-company",
      formData
    );
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("Error creating company:", error);
    return error.response.data;
  }
};

const updateCompanyService = (data) => {
  return axios.put(`/public/admin/api/update-company`, data);
};

const checkUserPhoneService = (phonenumber) => {
  return axios.get(`/public/check-phonenumber-user?phonenumber=${phonenumber}`);
};
const changePasswordByphone = (data) => {
  return axios.post(`/public/change-password`, data);
};
const handleChangePassword = (data) => {
  return axios.post(`/public/change-password`, data);
};

const getAllUsers = (data) => {
  return axios.get(
    `/public/get-all-user?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const createJobType = (data) => {
  return axios.post(`/public/create-jobType`, data);
};
const getAllJobType = (data) => {
  return axios.get(
    `/public/get-all-jobtype?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const DeleteJobtype = (codeId) => {
  return axios.delete(`/public/delete-jobtype?code=${codeId}`);
};
const UpdateJobtype = (data) => {
  return axios.put(`/public/update-jobtype`, data);
};
const getDetailJobTypeByCode = (code) => {
  return axios.get(`/public/get-detail-JobType-by-code?code=${code}`);
};
//, getDetailJobLevel, UpdateJobLevel

const createJobLevel = (data) => {
  return axios.post(`/public/create-jobLevel`, data);
};
const getAllJobLevel = (data) => {
  return axios.get(
    `/public/get-all-joblevel?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const DeleteJobLevel = (codeId) => {
  return axios.delete(`/public/delete-joblevel?code=${codeId}`);
};
const UpdateJobLevel = (data) => {
  return axios.put(`/public/update-joblevel`, data);
};
const getDetailJobLevelByCode = (code) => {
  return axios.get(`/public/get-detail-JobLevel-by-code?code=${code}`);
};

// createWorkType, getDetailWorkTypeByCode, UpdateWorkType, DeleteWorkType,getAllWorkType
const createWorkType = (data) => {
  return axios.post(`/public/create-worktype`, data);
};
const getAllWorkType = (data) => {
  return axios.get(
    `/public/get-all-worktype?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const DeleteWorkType = (codeId) => {
  return axios.delete(`/public/delete-worktype?code=${codeId}`);
};

const UpdateWorkType = (data) => {
  return axios.put(`/public/update-worktype`, data);
};

const getDetailWorkTypeByCode = (code) => {
  return axios.get(`/public/get-detail-WorkType-by-code?code=${code}`);
};

// createSalaryType, getDetailSalaryTypeByCode, UpdateSalaryType, DeleteSalaryType, getAllSalaryType
const createSalaryType = (data) => {
  return axios.post(`/public/create-salarytype`, data);
};
const getAllSalaryType = (data) => {
  return axios.get(
    `/public/get-all-salarytype?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const DeleteSalaryType = (codeId) => {
  return axios.delete(`/public/delete-salarytype?code=${codeId}`);
};

const UpdateSalaryType = (data) => {
  return axios.put(`/public/update-salarytype`, data);
};

const getDetailSalaryTypeByCode = (code) => {
  return axios.get(`/public/get-detail-SalaryType-by-code?code=${code}`);
};

//createExpType, getAllExpType, DeleteExpType, UpdateExpType, getDetailExpTypeByCode
const createExpType = (data) => {
  return axios.post(`/public/create-exptype`, data);
};
const getAllExpType = (data) => {
  return axios.get(
    `/public/get-all-exptype?limit=${data.limit}&offset=${data.offset}&search=${data.search}`
  );
};

const DeleteExpType = (codeId) => {
  return axios.delete(`/public/delete-exptype?code=${codeId}`);
};

const UpdateExpType = (data) => {
  return axios.put(`/public/update-exptype`, data);
};

const getDetailExpTypeByCode = (code) => {
  return axios.get(`/public/get-detail-ExpType-by-code?code=${code}`);
};


const BanUserService = (userId) => {
  return axios.post(`/api/ban-user`, {
    data: {
      id: userId,
    },
  });
};

const UnbanUserService = (userId) => {
  return axios.post(`/api/unban-user`, {
    data: {
      id: userId,
    },
  });
};

const AddNewUser = (data) => {
  return axios.post(`/api/create-new-user`, data);
};
const getListSkill = (data) => {
  return axios.get(`/public/get-list-skill?categoryJobCode=${data.categoryJobCode}&limit=${data.limit}&offset=${data.offset}&search=${data.search}`)
}
const getDetailSkillById = (id) => {
  return axios.get(`/public/getdetailsskillbyid?id=${id}`)
}

const DeleteSkillService = (skillId) => {
  return axios.delete(`/public/delete-skill-id?id=${skillId}`);
};
const createSkill = (data) => {
  return axios.post(`/public/createnewskill`, data)

}
const UpdateSkill = (data) => {
  return axios.put(`/public/updateskill`, data)

}


// thống kê 
const getStatisticalPackagePost = (data) => {
  return axios.get(`/api/get-statistical-package?limit=${data.limit}&offset=${data.offset}&fromDate=${data.fromDate}&toDate=${data.toDate}`)
}

const getStatisticalTypePost = (limit) => {
  return axios.get(`/public/get-statistical-post?limit=${limit}`)
}

const getStatisticalPackageCv = (data) => {
  return axios.get(`/api/get-statistical-package-cv?limit=${data.limit}&offset=${data.offset}&fromDate=${data.fromDate}&toDate=${data.toDate}`)
}
export {
  getListCompany,
  getDetailCompanyById,
  getListPostService,
  getListJobTypeAndCountPost,
  getAllCodeService,
  getDetailPostByIdService,
  handleLoginService,
  createNewUser,
  UpdateUserService,
  getDetailUserById,
  getAllSkillByJobCode,
  UpdateUserSettingService,
  getDetailCompanyByUserId,
  updateCompanyService,
  checkUserPhoneService,
  changePasswordByphone,
  createCompanyService,
  handleChangePassword,
  getAllUsers,
  BanUserService,
  UnbanUserService,
  AddNewUser,
  createJobType,
  getAllJobType,
  DeleteJobtype,
  UpdateJobtype,
  getDetailJobTypeByCode,
  getListSkill,
  DeleteSkillService,
  getDetailSkillById,

  UpdateSkill,
  createSkill,
  createJobLevel,
  getAllJobLevel,
  DeleteJobLevel,
  UpdateJobLevel,
  getDetailJobLevelByCode,

  createWorkType,
  getDetailWorkTypeByCode,
  UpdateWorkType,
  DeleteWorkType,
  getAllWorkType,

  createSalaryType,
  getDetailSalaryTypeByCode,
  UpdateSalaryType,
  DeleteSalaryType,
  getAllSalaryType,

  createExpType,
  getAllExpType,
  DeleteExpType,
  UpdateExpType,
  getDetailExpTypeByCode,

  getStatisticalPackagePost,
  getStatisticalTypePost,
  getStatisticalPackageCv

};
