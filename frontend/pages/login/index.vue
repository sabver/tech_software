<template>
	<view class="content">
		<view v-if="isShowPage">
			<view class="header">
				<image src="/static/logo.png"></image>
			</view>

			<view class="list">
				<view class="list-call">
					<image class="img" src="/static/1.png"></image>
					<input class="biaoti" v-model="account" type="text" placeholder="input the account" />
				</view>
				<view class="list-call">
					<image class="img" src="/static/2.png"></image>
					<input class="biaoti" v-model="password" type="text" maxlength="32" placeholder="input the password" password="true" />
				</view>

			</view>

			<view class="dlbutton" hover-class="dlbutton-hover" @tap="bindLogin()">
				<text>Login</text>
			</view>

			<view class="xieyi">
				<navigator url="forget" open-type="navigate">forget password</navigator>
				<text>|</text>
				<navigator url="reg" open-type="navigate">register</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		login
	} from '@/api/api.js'
	import {
		access
	} from '@/api/request.js'
	import {
		mapMutations
	} from 'vuex';
	import {
		getToken
	} from '../../service.js'	
	export default {
		components: {},
		data() {
			return {
				account: '',
				password: '',
				url: '',
				//标记是否渲染页面
				isShowPage: false,
			};
		},
		methods: {
			...mapMutations(['login']),
			bindLogin() {
				if (!this.account) {
					uni.showToast({
						icon: 'none',
						title: 'please input the account'
					});
					return;
				}
				if (!this.password) {
					uni.showToast({
						icon: 'none',
						title: 'please input the password'
					});
					return;
				}
				var $this = this
				var apiObj = login(this.account, this.password)
				access(apiObj, function(data) {
					console.log('login', data)
					$this.login(data.data)
					uni.showToast({
						icon: 'none',
						title: data.message
					})
					console.log('redirect_url',$this.url)
					uni.reLaunch({
						url: $this.url
					})
				})
			}
		},
		onLoad(option) {
			console.log('onLoad x',option)
			uni.showLoading({
				title: "loading..."
			})
			if (option.param) {
				this.url = decodeURIComponent(option.param) 
			} else {
				this.url = '/pages/index/index'
			}
			console.log('----------')
			//判断是否登录，是的话直接跳转
			console.log('getToken ----', getToken)
			let token = getToken()
			console.log('token',token)
			if (token) {				
				console.log('有token，直接跳转')
				uni.reLaunch({
					url: this.url
				})
				uni.hideLoading()
				return
			}
			console.log('没有token，保留')
			uni.hideLoading()
			this.isShowPage = true
		},		
	}
</script>

<style scoped lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		justify-content: center;
	}

	.header {
		width: 161upx;
		height: 161upx;
		background: $uni-color-primary;
		box-shadow: 0upx 12upx 13upx 0upx rgba($uni-color-primary, 0.47);
		border-radius: 50%;
		margin-top: 30upx;
		margin-left: auto;
		margin-right: auto;
	}

	.header image {
		width: 161upx;
		height: 161upx;
		border-radius: 50%;
	}

	.list {
		display: flex;
		flex-direction: column;
		padding-top: 50upx;
		padding-left: 70upx;
		padding-right: 70upx;
	}

	.list-call {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 100upx;
		color: #333333;
		border-bottom: 1upx solid rgba(230, 230, 230, 1);
	}

	.list-call .img {
		width: 40upx;
		height: 40upx;
	}

	.list-call .biaoti {
		flex: 1;
		text-align: left;
		font-size: 32upx;
		line-height: 100upx;
		margin-left: 16upx;
	}

	.dlbutton {
		color: #FFFFFF;
		font-size: 34upx;
		width: 470upx;
		height: 100upx;
		background: linear-gradient(-90deg, $uni-color-primary, rgba(188, 226, 158, 1));
		box-shadow: 0upx 0upx 13upx 0upx rgba(164, 217, 228, 0.2);
		border-radius: 50upx;
		line-height: 100upx;
		text-align: center;
		margin-left: auto;
		margin-right: auto;
		margin-top: 100upx;
	}

	.dlbutton-hover {
		background: linear-gradient(-90deg, rgba($uni-color-primary, 0.9), rgba(188, 226, 158, 0.9));
	}

	.xieyi {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 30upx;
		margin-top: 80upx;
		color: #FFA800;
		text-align: center;
		height: 40upx;
		line-height: 40upx;
	}

	.xieyi text {
		font-size: 24upx;
		margin-left: 15upx;
		margin-right: 15upx;
	}
</style>
