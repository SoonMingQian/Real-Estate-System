import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "https://real-estate-system-q28g.onrender.com/api/test/";

class UserService{
    getPublicContent(){
        return axios.get(API_URL + "all");
    }

    getSaleProperties(){
        return axios.get('https://real-estate-system-q28g.onrender.com/api/properties/sale');
    }

    getRentProperties(){
        return axios.get('https://real-estate-system-q28g.onrender.com/api/properties/rent');
    }
    
    getPropertyDetail(propertyId) {
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/property/${propertyId}`);
    }

    getApartment(){
        return axios.get('https://real-estate-system-q28g.onrender.com/api/apartment');
    }

    getCommercial(){
        return axios.get('https://real-estate-system-q28g.onrender.com/api/office');
    }

    addProperty(userId, data){
        return axios.post(`https://real-estate-system-q28g.onrender.com/api/add-property/${userId}`, data, { headers: authHeader() });
    }

    
    addPropertyImg(propertyId, images) {
        const formData = new FormData();
        for (let i = 0; i < images.length; i++) {
            formData.append('files', images[i].file);
        }

        return axios.post(`https://real-estate-system-q28g.onrender.com/api/add-property/${propertyId}/upload`, formData, {
            headers: {
                ...authHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    getEditProperty(propertyId) {
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/property/${propertyId}`, { headers: authHeader() });
    }

    editProperty(propertyId, data){
        return axios.put(`https://real-estate-system-q28g.onrender.com/api/updateproperty/${propertyId}`, data, { headers: authHeader() });
    }

    editPropertyImg(propertyId, images) {
        const formData = new FormData();
        for (let i = 0; i < images.length; i++) {
            formData.append('files', images[i].file);
        }
    
        return axios.post(`https://real-estate-system-q28g.onrender.com/api/add-property/${propertyId}/upload`, formData, {
            headers: {
                ...authHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    deleteFile(fileId){
        return axios.delete(`https://real-estate-system-q28g.onrender.com/api/file/${fileId}`, { headers: authHeader() });
    }

    deleteProperty(propertyId){
        return axios.delete(`https://real-estate-system-q28g.onrender.com/api/deleteproperty/${propertyId}`, { headers: authHeader() });
    }

    getMyProperties(userId){
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/user/${userId}`, { headers: authHeader() })
    }

    getProfile(userId){
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/useraccount/${userId}`, { headers: authHeader() });
    }

    updateProfile(userId, userData){
        return axios.put(`https://real-estate-system-q28g.onrender.com/api/updateuser/${userId}`, userData, { headers: authHeader() });
    }

    addToShortlist(userId, propertyId){
        return axios.post(`https://real-estate-system-q28g.onrender.com/api/users/shortlist/${userId}/${propertyId}`, { headers: authHeader() });
    }

    getShortlistedProperties(userId){
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/shortlist/${userId}`, { headers: authHeader() });
    }

    removeFromShortlist(userId, propertyId){
        return axios.delete(`https://real-estate-system-q28g.onrender.com/api/deleteshortlist/${userId}/${propertyId}`, { headers: authHeader() });
    }

    getEmailByPropertyId(propertyId){
        return axios.get(`https://real-estate-system-q28g.onrender.com/api/property/${propertyId}/userEmail`, { headers: authHeader() });
    }

}

export default new UserService();