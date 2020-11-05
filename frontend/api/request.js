import {
	getToken,
	addToken,
	delToken
} from '../service.js'
import {
	domain
} from './domain'
export function access(apiObj, successCallback, failCallback, isNotShowLoading) {
	if (!isNotShowLoading) {
		uni.showLoading({
			title: 'loading...'
		});
	}
	let Xtoken = getToken()
	console.log(Xtoken)
	uni.request({
		url: apiObj.url,
		data: apiObj.param,
		method: apiObj.method,
		dataType:'json',
		success: (res) => {
			console.log('request success',res)
			if (!isNotShowLoading) {
				uni.hideLoading()
			}
			if (res.data.status == 100) {
				successCallback ? successCallback(res.data) : null
			} else if (res.data.status == 300 || res.data.status == 600 || res.data.status == 700) {
				delToken()
				//需要登录的逻辑
				uni.showToast({
					icon: 'none',
					title: res.data.message,
				});
				let pageList = getCurrentPages()
				let curUrl = '/'+pageList[pageList.length - 1].route
				setTimeout(function(){
					uni.reLaunch({
						//参数名称不可以是url,否则会出bug!!!!
						url: '/pages/login/index?param=' + encodeURIComponent(curUrl)
					})	
				},60)			
			} else {
				uni.showToast({
					icon: 'none',
					title: res.data.message,
				});
				failCallback ? failCallback(res) : null
			}

		},
		header: {
			'content-type': 'application/x-www-form-urlencoded',
			'Authorization': Xtoken
		},
		fail: (res) => {
			console.log('request fail',res)
			if (!isNotShowLoading) {
				uni.hideLoading()
			}

			if (!failCallback) {
				uni.showToast({
					icon: 'none',
					title: 'net error',
				});
			}
			failCallback ? failCallback(res) : null
		},
	})
}
