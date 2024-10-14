import axios from "axios"

const getAllListCvByUserIdService = (data) => {
    return axios.get(`http://localhost:8080/public/cv/get-all-cv-by-userId?limit=${data.limit}&offset=${data.offset}&userId=${data.userId}`)
}

const getDetailCvService = (id,roleCode) => {
    return axios.get(`/api/get-detail-cv-by-id?cvId=${id}&roleCode=${roleCode}`)
}

const getStatisticalCv = (data) => {
    return axios.get(`/api/get-statistical-cv?limit=${data.limit}&offset=${data.offset}&fromDate=${data.fromDate}&toDate=${data.toDate}&companyId=${data.companyId}`)
}
// when send cv new => send and create new
const createNewCv = async  (data,token) => {
    try {
        
        // Thực hiện request POST với headers Authorization
        let response = await axios.post('http://localhost:8080/public/createCVnew', data, {
            headers: {
                'Authorization': `Bearer ${token}` // Thêm token vào header
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error creating CV:', error);
        throw error;
    }
}

export {
     getAllListCvByUserIdService,
     getDetailCvService,
     getStatisticalCv,
     createNewCv
}