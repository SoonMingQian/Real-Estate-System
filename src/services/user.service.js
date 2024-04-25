import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/test/";

class UserService{
    getPublicContent(){
        return axios.get(API_URL + "all");
    }

    getSaleProperties(){
        return axios.get('http://localhost:8080/api/properties/sale');
    }

    getRentProperties(){
        return axios.get('http://localhost:8080/api/properties/rent');
    }
    
    getPropertyDetail(propertyId) {
        return axios.get(`http://localhost:8080/api/property/${propertyId}`);
    }

    getApartment(){
        return axios.get('http://localhost:8080/api/apartment');
    }

    getCommercial(){
        return axios.get('http://localhost:8080/api/office');
    }

    addProperty(userId, data){
        return axios.post(`http://localhost:8080/api/add-property/${userId}`, data, { headers: authHeader() });
    }

    
    addPropertyImg(propertyId, images) {
        const formData = new FormData();
        for (let i = 0; i < images.length; i++) {
            formData.append('files', images[i].file);
        }

        return axios.post(`http://localhost:8080/api/add-property/${propertyId}/upload`, formData, {
            headers: {
                ...authHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    getEditProperty(propertyId) {
        return axios.get(`http://localhost:8080/api/property/${propertyId}`, { headers: authHeader() });
    }

    editProperty(propertyId, data){
        return axios.put(`http://localhost:8080/api/updateproperty/${propertyId}`, data, { headers: authHeader() });
    }

    editPropertyImg(propertyId, images) {
        const formData = new FormData();
        for (let i = 0; i < images.length; i++) {
            formData.append('files', images[i].file);
        }
    
        return axios.post(`http://localhost:8080/api/add-property/${propertyId}/upload`, formData, {
            headers: {
                ...authHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    deleteFile(fileId){
        return axios.delete(`http://localhost:8080/api/file/${fileId}`, { headers: authHeader() });
    }

    deleteProperty(propertyId){
        return axios.delete(`http://localhost:8080/api/deleteproperty/${propertyId}`, { headers: authHeader() });
    }

    getMyProperties(userId){
        return axios.get(`http://localhost:8080/api/user/${userId}`, { headers: authHeader() })
    }

    getProfile(userId){
        return axios.get(`http://localhost:8080/api/useraccount/${userId}`, { headers: authHeader() });
    }

    updateProfile(userId, userData){
        return axios.put(`http://localhost:8080/api/updateuser/${userId}`, userData, { headers: authHeader() });
    }

    addToShortlist(userId, propertyId){
        return axios.post(`http://localhost:8080/api/users/shortlist/${userId}/${propertyId}`, { headers: authHeader() });
    }

    getShortlistedProperties(userId){
        return axios.get(`http://localhost:8080/api/shortlist/${userId}`, { headers: authHeader() });
    }

    removeFromShortlist(userId, propertyId){
        return axios.delete(`http://localhost:8080/api/deleteshortlist/${userId}/${propertyId}`, { headers: authHeader() });
    }

    getEmailByPropertyId(propertyId){
        return axios.get(`http://localhost:8080/api/property/${propertyId}/userEmail`, { headers: authHeader() });
    }

}

export default new UserService();