import {
	domain
} from './domain'
//登录
export function login(account, password) {
	return {
		url: domain() + 'user/login',
		method: 'post',
		param: {
			account: account,
			password: password
		}
	}
}
// 全部查询
export function findAll(curPage, pageSize) {
	return {
		url: domain() + 'paper/findAll',
		method: 'get',
		param: {
			curPage: curPage,
			pageSize: pageSize
		}
	}
}
//关键词查询
export function find(keyword, curPage, pageSize) {
	return {
		url: domain() + 'paper/find',
		method: 'get',
		param: {
			curPage: curPage,
			pageSize: pageSize,
			keyword: keyword
		}
	}
}

//详情查询
export function detail(id){
	return {
		url: domain() + 'paper/detail',
		method: 'get',
		param: {
			id: id
		}		
	}
}
//收藏
export function collect(id){
	return {
		url:domain() + 'paper/collect',
		method:'post',
		param:{
			id:id
		}
	}
}
//不收藏
export function nocollect(id){
	return {
		url:domain() + 'paper/nocollect',
		method:'post',
		param:{
			id:id
		}
	}
}
//我的推荐列表
export function findMy(curPage, pageSize){
	return {
		url:domain() + 'paper/findMy',
		method: 'get',
		param: {
			curPage: curPage,
			pageSize: pageSize
		}		
	}
}
//我的收藏列表
export function collectList(curPage, pageSize){
	return {
		url:domain() + 'paper/collectList',
		method: 'get',
		param: {
			curPage: curPage,
			pageSize: pageSize
		}		
	}	
}
