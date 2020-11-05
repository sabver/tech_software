// 本地用户信息
const USER_KEY = 'USER_KEY';
// token信息
const TOKEN_KEY = 'TOKEN_KEY';

export function getUser() {
	var ret = '';
	ret = uni.getStorageSync(USER_KEY);
	if (!ret) {
		ret = '{}';
	}
	return JSON.parse(ret);
}

export function addUser(userInfo) {
	uni.setStorageSync(USER_KEY, JSON.stringify(userInfo));
}

export function getToken() {
	var ret = '';
	ret = uni.getStorageSync(TOKEN_KEY);
	console.log('getToken service.js',ret)
	if (!ret) {
		ret = '';
	}
	return ret;
}

export function addToken(token) {
	uni.setStorageSync(TOKEN_KEY, token);
}

export function delToken() {
	uni.setStorageSync(TOKEN_KEY, '');
}

//清除本地缓存
export function clearAll() {
	let array = ['USER_KEY', 'TOKEN_KEY']
	for (var i = 0; i < array.length; i++) {
		uni.setStorageSync(array[i], '')
	}
}
