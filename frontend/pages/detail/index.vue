<template>
	<view class="">
		<view class='c-container'>
			<view class='c-detail-container bs'>
				<view class='c-detail-title bs'>
					<text>{{title}}</text>
				</view>
				<view class='c-loading' :class='[!isLoad?"hidden":""]' :style="{
				'padding-top':paddingTop+'px'
			}">
					<loading :is-loading="isLoad"></loading>
				</view>
				<view :class='[isLoad?"hidden":""]'>
					<view class='c-detail__toolbar bfc'>
						<view class='c-detail__tag'>
							<text>{{typeName}}</text>
						</view>
						<view class='c-detail__author c-detail__tip-text'>
							<text>authors：{{authors}}</text>
						</view>
					</view>

					<view class='c-detail-html'>
						<!-- <rich-text :nodes="katexHtml"></rich-text> -->
						<text class="text-div">{{content}}</text>
					</view>
					<!-- 		      <view class='c-detail__time'>
		        <text>{{date}} 发布</text>
		      </view> -->
					<!-- 		      <view class='c-detail__remark c-detail__remark--first'>
		        <text>特别声明：</text>
		      </view>
		      <view class='c-detail__remark'>
		        <text>本文为币博士平台“{{obj.editor.nickname}}”上传并发布，仅代表发布者观点。币博士仅提供信息发布平台。如转载涉及版权等问题，请联系我们进行删除或支付稿费。</text>
		      </view>
		      <view class='c-detail__qrcode'>
		        <image src='../../static/qrcode.jpg' mode='widthFix'></image>
		      </view> -->
					<!-- <view class='c-detail__zan-btn'>
		        <view class='c-detail__zan-warp'>
		          <button bindtap='like'><image class='c-detail__zan-icon' src='../../static/heart--white.png' mode='widthFix'></image>         赞一个</button>
		        </view>        
		      </view> -->
				</view>
			</view>
		</view>
		<view class='c-bnav bs' :class='[isIphoneX?"c-bnav--iphonex":""]'>
			<view class='bfc'>
				<view class='pull-left w50_ bs'>
					<view class='c-bnav__box relative bs'>
						<image mode='widthFix' src='../../static/bicon_love.png' v-if="!isCollect" @tap="collect"></image>
						<image mode='widthFix' src='../../static/heart--active.png' v-if="isCollect" @tap="nocollect"></image>
					</view>
				</view>
				<view class='pull-left w50_ bs bfc'>
					<view class="pull-left w50_ c-mydiv__download" @tap="download">
						<image mode='widthFix' src='../../static/download.png' class="c-myimg__download"></image>
					</view>
					<view class="pull-left w50_ c-mydiv__home" @tap="goBack">
						<image mode='widthFix' src='../../static/home_blue.png' class="c-myimg__home"></image>
					</view>					
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		detail,collect,nocollect
	} from '@/api/api.js'
	import {
		access
	} from '@/api/request.js'
	import loading from '@/components/loading/loading.vue'

	export default {
		components: {
			loading
		},
		/**
		 * 页面的初始数据
		 */
		data() {
			return {
				title: '',
				content: '',
				id: '',
				obj: {},
				isLoad: true,
				paddingTop: getApp().globalData.windowHeight / 2 - 100,
				isCollect: false,
				authors: '',
				typeName: '',
				//是否是iphoneX
				isIphoneX: getApp().globalData.model.indexOf('iPhone X') != -1,
			}
		},

		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log(options)
			if (!options.id) {
				uni.showToast({
					title: 'param error',
					icon: 'none'
				})
				return
			} else {
				this.id = options.id
			}
			if (options.title) {
				this.title = options.title
			}
			this.load()
			// console.log('detail.....',katex)			
		},

		methods: {
			collect() {
				uni.showLoading({
					title: 'loading...'
				});
				var $this = this
				var apiObj = collect(this.id)
				access(apiObj, function(res) {
					console.log('collect', res)
					uni.hideLoading()
					$this.isCollect = true
				}, function() {
					uni.hideLoading()
				})				
			},
			nocollect() {
				uni.showLoading({
					title: 'loading...'
				});
				var $this = this
				var apiObj = nocollect(this.id)
				access(apiObj, function(res) {
					console.log('nocollect', res)
					uni.hideLoading()
					$this.isCollect = false
				}, function() {
					uni.hideLoading()
				})
			},
			download(){
				
			},
			load: function() {
				uni.showNavigationBarLoading()
				this.isLoad = true
				var $this = this
				var apiObj = detail(this.id)
				access(apiObj, function(res) {
					console.log('detail', res)
					$this.isLoad = false
					uni.hideNavigationBarLoading()
					$this.title = res.data.paper.title
					$this.content = res.data.paper.abs
					$this.isCollect = res.data.collect
					$this.typeName = res.data.paper.typeName
					$this.authors = res.data.paper.authors
					setTimeout(function(){
						console.log($this.isCollect)
					},1000)
				}, function() {
					$this.isLoad = false
					uni.hideNavigationBarLoading()
				})
			},
			goBack(){
				uni.navigateBack({
					delta:1
				})
			}
		},
	}
</script>

<style>
	.c-bnav__image--home {
		left: 20rpx;
	}

	.c-bnav__image--download {
		left: 40rpx;
	}

	.c-detail-container {
		padding: 0 30rpx;
	}

	.c-detail-title {
		font-size: 44rpx;
		color: #121212;
		font-weight: bold;
	}

	.c-detail__toolbar {
		margin-top: 20rpx;
	}

	.c-detail__tip-text {
		font-size: 28rpx;
		color: #bcbcbc;
	}

	.c-detail__zan {
		position: relative;
		font-size: 24rpx;
	}

	.c-detail__zan--active {
		color: #b03e3e;
	}

	.c-detail__zan image {
		position: absolute;
		top: 6rpx;
		right: 50rpx;
		width: 28rpx;
	}

	.c-detail-html {
		margin-top: 35rpx;
		font-size: 28rpx;
		padding-bottom: 120rpx;
		line-height: 1.6;
	}

	.c-detail__time {
		margin-top: 34rpx;
		font-size: 28rpx;
		color: #999;
	}

	.c-detail__remark {
		margin-top: 0;
		font-size: 28rpx;
		color: #999;
	}

	.c-detail__remark--first {
		margin-top: 34rpx;
	}

	.c-detail__qrcode {
		margin-top: 60rpx;
		text-align: center;
		padding-bottom: 150rpx;
	}

	.c-detail__qrcode image {
		width: 274rpx;
	}

	.c-detail__zan-btn {
		margin-top: 70rpx;
		padding-bottom: 60rpx;
	}

	.c-detail__zan-warp {
		width: 334rpx;
		margin: 0 auto;
	}

	.c-detail__zan-btn button {
		width: 334rpx;
		color: white;
		background-image: -webkit-linear-gradient(left, #81c3f3 0%, #4a90e2 100%);
		background-image: linear-gradient(90deg, #81c3f3 0%, #4a90e2 100%);
		-webkit-border-radius: 52rpx;
		border-radius: 52rpx;
	}

	.c-detail__zan-icon {
		width: 34rpx;
	}

	.c-bnav {
		height: 88rpx;
		padding: 0 40rpx;
		-webkit-box-shadow: 0 0 8rpx 0 rgba(54, 54, 54, 0.5);
		box-shadow: 0 0 8rpx 0 rgba(54, 54, 54, 0.5);
		position: fixed;
		left: 0;
		right: 0;
		bottom: 0;
		background: white;
		z-index: 100;
	}

	.c-bnav__box {
		padding: 0 10rpx;
		background-color: #f3f3f3;
		-webkit-border-radius: 8rpx;
		border-radius: 8rpx;
		display: inline-block;
		padding-left: 66rpx;
		height: 48rpx;
		line-height: 44rpx;
		margin-top: 20rpx;
	}

	/* 	.c-bnav__box::before {
		position: absolute;
		left: 136rpx;
		top: 4rpx;
		content: "|";
		color: lightgray;
	} */

	.c-bnav__box text {
		color: #333;
		font-size: 20rpx;
	}

	.c-bnav__zan--left {
		display: inline-block;
	}

	.c-bnav__zan--right {
		display: inline-block;
		margin-left: 10rpx;
	}

	.c-bnav__box image {
		width: 34rpx;
		position: absolute;
		left: 20rpx;
		top: 10rpx;
	}

	.c-detail__tag {
		font-size: 24rpx;
		color: white;
		/* width: 40px; */
		height: 36rpx;
		line-height: 36rpx;
		background-image: -webkit-linear-gradient(left, #81c3f3 0%, #4a90e2 100%);
		background-image: linear-gradient(90deg, #81c3f3 0%, #4a90e2 100%);
		-webkit-border-radius: 6rpx;
		border-radius: 6rpx;
		display: inline-block;
		padding: 0 20rpx;
	}

	/* .c-bnav__image{
  height: 38rpx;
}

.c-bnav__image-box{
  margin-top: 14rpx;
}

.c-bnav__image image{
  width: 44rpx;
}

.c-bnav__image-box--share image{
  width: 38rpx;
}

.c-bnav__title{
  font-size: 20rpx;
  color: #333;
}

.c-bnav__image-box--index{
  margin-right: 50rpx;
} */

	.c-bnav__image {
		height: 38rpx;
	}

	.c-bnav__image-box {
		height: 88rpx;
	}

	.c-bnav__image-box button {
		line-height: 1.43;
		border: 0 solid white;
		padding: 0;
		font-size: 20rpx;
		box-sizing: border-box;
		height: 88rpx;
		padding-top: 48rpx;
		color: #333;
	}

	.c-bnav__image {
		position: absolute;
		top: 8rpx;
		height: 38rpx;
		text-align: center;
	}

	.c-bnav__image image {
		width: 44rpx;
	}

	.c-bnav__image-box--share image {
		width: 38rpx;
	}

	.c-bnav__image-box--index {
		margin-right: 50rpx;
	}

	.c-bnav--iphonex {
		height: 125rpx;
	}
	.c-myimg__home,
	.c-myimg__download{
		width: 46rpx;
		height: 46rpx;
	}
	.c-mydiv__download,
	.c-mydiv__home{
		text-align: right;
		margin-top: 16rpx;
	}
</style>
