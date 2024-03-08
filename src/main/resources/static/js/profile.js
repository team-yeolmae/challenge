async function getProfile(email){
    const response = await axios.get(`/user/api/mypage/profile`)
    return response.data
}

async function updateProfile(profileObj){
    const response = await axios.put(`/user/api/mypage/updateProfile`, profileObj)
    return response.data
}