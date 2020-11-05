<template>
	<view class="c-page">
		<view class='c-nav bfc' :class='[isShowNavSearch?"c-nav--active":""]'>
			<view class='bfc'>
				<view class='c-nav__container pull-left w50_ text-right'>
					<view class='c-nav__item c-nav__item--left text-center' :class='[tabCurrent==0?"c-nav__item--active":""]' @tap='changeCurrent'
					 data-current="0">
						<text>All</text>
					</view>
				</view>
				<view class='c-nav__container pull-right w50_'>
					<view class='c-nav__item text-center' :class='[tabCurrent==1?"c-nav__item--active":""]' @tap='changeCurrent'
					 data-current="1">
						<text>Rec</text>
					</view>
				</view>
			</view>
			<view class='c-line' :class='[tabCurrent==0?"c-line--to-left":"c-line--to-right"]'>
				<view class='c-line__container'>
					<view class='c-line__content'></view>
				</view>
			</view>
			<view class='c-nav__search bs' :class='[isShowNavSearch?"c-nav__search--active":""]' @tap='goToSearch'>
				<image mode='widthFix' src='/static/search--normal.png'></image>
			</view>
			<view class='c-nav__search c-nav__search--refresh bs' :class='[isShowNavSearch?"":"c-nav__search--active"]' @tap='search'>
				<image mode='widthFix' src='/static/refresh.png'></image>
			</view>
		</view>
		<swiper :indicator-dots="indicatorDots" :autoplay="autoplay" :circular="circular" :vertical="vertical" :interval="interval"
		 :duration="duration" :previous-margin="previousMargin+'px'" :next-margin="nextMargin+'px'" @change="swiperChange"
		 :current="current">
			<swiper-item>
				<scroll-view scroll-y="true" @scrolltolower="bindscrolltolowerLeft" @scroll="bindscrollLeft" @touchend='leftTouchEnd'>
					<view class='c-search'>
						<view class='c-search__container' hover-class='c-search__container--active' @tap='goToSearch'>
							<view class='c-search__text'>
								<text>search</text>
								<image mode='widthFix' class="c-search__icon--normal" src='/static/search--normal.png'></image>
								<image mode='widthFix' class="c-search__icon--active" src='/static/search--active.png'></image>
							</view>
						</view>
					</view>
					<view class='wrapper'>
						<view class='c-index-content'>
							<view class='c-index-list'>
								<view v-for="(item,index) in listLeft" class='c-currency-item' :key="item.id" :class='[index==0?"c-currency-item--first":""]' @tap="goToDetail" :data-id="item.id">
									<list-item :title="item.title" :typeName="item.typeName"></list-item>
								</view>
								<view class='c-tip'>
									<loading :is-loading="isLoadListLeft"></loading>
									<view class='c-tip__text' :class='[isLoadListLeft==false && isLoadEndLeft==true?"":"hidden"]'>
										<text>no more data...</text>
									</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>

			<swiper-item>
				<scroll-view scroll-y="true" @scrolltolower="bindscrolltolowerRight" @scroll="bindscrollRight" @touchend='rightTouchEnd'>
					<view class='c-search'>
						<view class='c-search__container' hover-class='c-search__container--active' @tap='goToSearch'>
							<view class='c-search__text'>
								<text>search</text>
								<image mode='widthFix' class="c-search__icon--normal" src='/static/search--normal.png'></image>
								<image mode='widthFix' class="c-search__icon--active" src='/static/search--active.png'></image>
							</view>
						</view>
					</view>
					<view class='wrapper'>
						<view class='c-index-content'>
							<view class='c-index-list'>
								<view v-for="(item,index) in listRight" :key="item.id" class='c-list-item' :class='[index==0?"c-list-item--first":""]' @tap="goToDetail" :data-id="item.id">
									<list-item :title="item.title" :typeName="item.typeName"></list-item>
								</view>
								<view class='c-tip'>
									<loading :is-loading="isLoadListRight"></loading>
									<view class='c-tip__text' :class='[isLoadListRight==false && isLoadEndRight==true?"":"hidden"]'>
										<text>no more data...</text>
									</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>

</template>

<script>
	import {
		findMy,
		findAll
	} from '@/api/api.js'
	import {
		access
	} from '@/api/request.js'
	import loading from '@/components/loading/loading.vue'
	import listItem from '@/components/list_item/list_item.vue'
	export default {
		components: {
			loading,
			listItem
		},
		data() {
			return {
				indicatorDots: false,
				vertical: false,
				autoplay: false,
				circular: false,
				interval: 2000,
				duration: 500,
				previousMargin: 0,
				nextMargin: 0,
				current: 0,
				tabCurrent: 0,
				paramScrollTop: -100,
				showSearchIconTop: 30,
				isShowNavSearch: false,
				//现在外面这个页面用不到
				keyword: '',
				listLeft: [],
				isLoadListLeft: false,
				isLoadEndLeft: false,
				pageSizeLeft: 10,
				curPageLeft: 1,

				listRight: [],
				isLoadListRight: false,
				isLoadEndRight: false,
				pageSizeRight: 10,
				curPageRight: 1,

				timeidLeft: '',
				timeidRight: '',

				leftScrollTop: 0,
				rightTcrollTop: 0,
				//滚动的时候，先把要执行的函数保存起来，执行完之后自我销毁
				leftTouchEndFun: function() {},
				rightTouchEndFun: function() {}
				// isShowLoginModal:false,
			}
		},
		onLoad: function() {
			console.log('onLoad')
			this.loadList(true, true)
			this.loadList(true, false)
		},
		onHide: function() {
			uni.hideNavigationBarLoading()
		},
		methods: {
			//因为从小程序搬到uni-app，所以需要兼容setData方法
			setData: function(obj) {
				let that = this;
				let keys = [];
				let val, data;
				Object.keys(obj).forEach(function(key) {
					keys = key.split('.');
					val = obj[key];
					data = that.$data;
					keys.forEach(function(key2, index) {
						if (index + 1 == keys.length) {
							that.$set(data, key2, val);
						} else {
							if (!data[key2]) {
								that.$set(data, key2, {});
							}
						}
						data = data[key2];
					})
				});
			},
			/**
			 * isReset 是否重置查询
			 * isLeft 是否是左边的布局查询
			 */
			loadList: function(isReset, isLeft) {
				console.log('loadList')
				if (isLeft) {
					if (this.isLoadListLeft == true) {
						return
					}
				} else {
					if (this.isLoadListRight == true) {
						return
					}
				}
				if (isReset) {
					if (isLeft) {
						this.setData({
							curPageLeft: 1,
							isLoadEndLeft: false,
							isLoadListLeft: true
						})
					} else {
						this.setData({
							curPageRight: 1,
							isLoadEndRight: false,
							isLoadListRight: true
						})
					}
					this.vibrateShort()
				} else {
					if (isLeft) {
						if (this.isLoadEndLeft) {
							return
						}
						this.setData({
							isLoadListLeft: true
						})
					} else {
						if (this.isLoadEndRight) {
							return
						}
						this.setData({
							isLoadListRight: true
						})
					}
				}
				uni.showNavigationBarLoading()
				var app = getApp()
				var self = this
				var apiObj = findMy(this.curPageRight, this.pageSizeRight)
				if (isLeft) {
					apiObj = findAll(this.curPageLeft, this.pageSizeLeft)
				}
				access(apiObj, function(res) {
					console.log(res.data)
					var list = res.data
					if (isLeft) {
						var leftObj = {
							listLeft: isReset ? list : app.globalData.merge(self.listLeft, list),
							isLoadListLeft: false,
							isLoadEndLeft: self.isLoadEndLeft,
							curPageLeft: self.curPageLeft
						}
						//判断是否可以继续下一页
						if (list.length < self.pageSizeLeft) {
							leftObj.isLoadEndLeft = true
						} else {
							leftObj.isLoadEndLeft = false
							leftObj.curPageLeft++
						}
						self.setData(leftObj)
					} else {
						console.log('right',list)
						var rightObj = {
							listRight: isReset ? list : app.globalData.merge(self.listRight, list),
							isLoadListRight: false,
							isLoadEndRight: self.isLoadEndRight,
							curPageRight: self.curPageRight
						}
						//判断是否可以继续下一页
						if (list.length < self.pageSizeRight) {
							rightObj.isLoadEndRight = true
						} else {
							rightObj.isLoadEndRight = false
							rightObj.curPageRight++
						}
						self.setData(rightObj)
					}
					uni.stopPullDownRefresh()
					uni.hideNavigationBarLoading()
				}, function(data) {
					console.log('fail', data)
					if (isLeft) {
						self.setData({
							isLoadListLeft: false
						})
					} else {
						self.setData({
							isLoadListLeft: false
						})
					}
					uni.stopPullDownRefresh()
					uni.hideNavigationBarLoading()
				}, true)
			},

			vibrateShort: function() {
				try {
					if (uni.vibrateShort) {
						uni.vibrateShort()
					}
				} catch (e) {
					console.log(e)
				}
			},

			goToSearch: function() {
				uni.navigateTo({
					url: "../search/index"
				})
			},

			// bindscrolltoupperLeft: function() {
			//   console.log('bindscrolltoupperLeft')
			// },

			bindscrolltolowerLeft: function() {
				// console.log('bindscrolltolowerLeft')
				// this.vibrateShort()
				this.loadList(false, true)
			},

			bindscrolltolowerRight: function() {
				// this.vibrateShort()
				this.loadList(false, false)
			},

			/**
			 * tab被点击change之后的事件
			 */
			changeCurrent: function(e) {
				// console.log(e)
				var current = e.currentTarget.dataset.current
				this.setData({
					current: current
				})
			},
			/**
			 * swiper change之后的事件
			 */
			swiperChange: function(e) {
				// console.log(e.detail)
				this.setData({
					tabCurrent: e.detail.current
				})
			},

			goToDetail: function(e) {				
				console.log('goToDetail',e.currentTarget.dataset.id)
				uni.navigateTo({
					url: "../detail/index?id=" + e.currentTarget.dataset.id
				})
			},

			bindscrollLeft: function(e) {
				clearTimeout(this.timeidLeft)
				var self = this
				this.setData({
					timeidLeft: setTimeout(function() {
						self.realScrollFunLeft(e)
					}, 10)
				})
			},

			realScrollFunLeft: function(e) {
				var scrollTop = e.detail.scrollTop
				var self = this
				this.setData({
					leftScrollTop: scrollTop
				})
				if (scrollTop < this.paramScrollTop) {
					//委托
					this.setData({
						leftTouchEndFun: function() {
							self.loadList(true, true)
						}
					})
					// this.loadList(true, true)
				} else {
					if (scrollTop > this.showSearchIconTop) {
						this.setData({
							isShowNavSearch: true
						})
					} else {
						this.setData({
							isShowNavSearch: false
						})
					}
				}
			},

			bindscrollRight: function(e) {
				clearTimeout(this.timeidRight)
				var self = this
				this.setData({
					timeidRight: setTimeout(function() {
						self.realScrollFunRight(e)
					}, 10)
				})
			},

			realScrollFunRight: function(e) {
				var scrollTop = e.detail.scrollTop
				var self = this
				this.setData({
					rightScrollTop: scrollTop
				})
				if (scrollTop < this.paramScrollTop) {
					//委托
					this.setData({
						rightTouchEndFun: function() {
							self.loadList(true, false)
						}
					})
					// this.loadList(true, false)
				} else {
					if (scrollTop > this.showSearchIconTop) {
						this.setData({
							isShowNavSearch: true
						})
					} else {
						this.setData({
							isShowNavSearch: false
						})
					}
				}
			},

			/**
			 * 页面相关事件处理函数--监听用户下拉动作
			 */
			onPullDownRefresh: function() {
				if (this.tabCurrent == '0') {
					this.loadList(true, true)
				} else {
					this.loadList(true, false)
				}
			},

			search: function() {
				if (this.tabCurrent == '0') {
					this.loadList(true, true)
				} else {
					this.loadList(true, false)
				}
			},

			leftTouchEnd: function() {
				// uni.showToast({
				//   title: 'leftTouchEnd',
				// })
				this.leftTouchEndFun()
				//使用之后自我销毁
				this.setData({
					leftTouchEndFun: function() {}
				})
				// console.log(this.leftTouchEndFun)
			},

			rightTouchEnd: function() {
				// uni.showToast({
				//   title: 'rightTouchEnd',
				// })
				this.rightTouchEndFun()
				//使用之后自我销毁
				this.setData({
					rightTouchEndFun: function() {}
				})
			},
		},


	}
</script>

<style>
	page {
		height: 100%;
		font-size: 28rpx;
		background: #efefef;
	}

	.c-page {
		height: 100%;
	}

	swiper {
		height: 100%;
	}

	scroll-view {
		height: 100%;
		padding-top: 100rpx;
		box-sizing: border-box;
		/* background: white; */
	}

	.swiper-item {
		display: block;
		height: 100%;
	}

	.c-nav {
		width: 100%;
		padding: 10rpx 0;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		background: white;
		z-index: 1;
		height: 100rpx;
		box-sizing: border-box;
	}

	.c-nav--active {
		-webkit-box-shadow: 0px 2px 4px 0px rgba(192, 192, 192, 0.5);
		box-shadow: 0px 2px 4px 0px rgba(192, 192, 192, 0.5);
	}

	.c-nav__item {
		width: 150rpx;
		font-size: 32rpx;
		font-weight: bold;
	}

	.c-nav__item--active {
		color: #4a90e2;
	}

	.c-nav__item--left {
		display: inline-block;
	}

	.c-nav__search {
		background: #f3f3f3;
		text-align: center;
		-webkit-border-radius: 50%;
		border-radius: 50%;
		height: 46rpx;
		width: 46rpx;
		padding-top: 9rpx;
		position: absolute;
		right: 6%;
		top: 16rpx;
		opacity: 0;
		z-index: 1;
		-webkit-transition: all ease 0.2s;
		transition: all ease 0.2s;
	}

	/* .c-nav__search--refresh {
  padding-top: 6rpx;
} */

	.c-nav__search--active {
		opacity: 1;
		z-index: 2;
	}

	.c-nav__search icon {
		display: block;
	}

	.c-nav__search image {
		width: 28rpx;
	}

	.c-line {
		width: 100%;
		height: 6rpx;
		-webkit-transform: translate3d(30%, 0, 0);
		transform: translate3d(30%, 0, 0);
		-webkit-transition: all ease 0.2s;
		transition: all ease 0.2s;
	}

	.c-line__container {
		width: 150rpx;
		text-align: center;
	}

	.c-line__content {
		background: #4a90e2;
		background: -webkit-linear-gradient(left, #81c3f3 0%, #4a90e2 100%);
		background-image: linear-gradient(90deg, #81c3f3 0%, #4a90e2 100%);
		height: 6rpx;
		width: 108rpx;
		display: inline-block;
		-webkit-border-radius: 4rpx;
		border-radius: 4rpx;
	}

	.c-line--to-left {
		-webkit-transform: translate3d(30%, 0, 0);
		transform: translate3d(30%, 0, 0);
	}

	.c-line--to-right {
		-webkit-transform: translate3d(50%, 0, 0);
		transform: translate3d(50%, 0, 0);
	}

	.c-search {
		/* margin-top: 32rpx; */
		padding: 20rpx 20rpx;
		background: white;
	}

	.c-search__container {
		border-radius: 40rpx;
		background: #f3f3f3;
		text-align: center;
		padding: 14rpx 0;
	}

	.c-search__icon {
		display: inline-block;
		padding-top: 10rpx;
	}

	.c-search__text {
		position: relative;
		width: 200rpx;
		display: inline-block;
		color: #b5b5b5;
		font-size: 28rpx;
	}

	.c-search__text icon {
		position: absolute;
		left: 6rpx;
		top: 6rpx;
	}

	.c-search__text image {
		position: absolute;
		left: 6rpx;
		top: 6rpx;
		width: 24rpx;
	}

	.c-search__icon--normal {
		display: block;
	}

	.c-search__icon--active {
		display: none;
	}

	.c-search__container--active .c-search__icon--normal {
		display: none;
	}

	.c-search__container--active .c-search__icon--active {
		display: block;
	}

	.c-search__container--active .c-search__text {
		color: #4a90e2;
	}

	.c-list-item {
		margin-top: 10rpx;
	}

	.c-list-item--first {
		margin-top: 0;
	}

	.c-currency-item {
		margin-top: 10rpx;
	}

	.c-currency-item--first {
		margin-top: 0;
	}
</style>
