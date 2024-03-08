async function updateProfile(profileObj){
    const response = await axios.put(`/user/api/mypage/profile`, profileObj)
    return response.data
}