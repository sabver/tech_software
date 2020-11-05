<template>
	<view class="c-page">
		<view class='c-up bs'>
			<view class='c-search bfc bs'>
				<view class='c-input pull-left'>
					<input class='c-input__input' focus placeholder='搜索热点' confirm-type="搜索" @confirm="confirm" @input="onKeyInput"
					 maxlength='10'></input>
					<icon class='c-input__icon' type='search' size="14" color="#b5b5b5"></icon>
					<icon class='c-input__icon--clear' :class='[value?"":"hidden"]' type='clear' size="14" color="#b5b5b5" @tap='cancelLight'></icon>
				</view>
				<view class='c-cancel pull-right text-center' hover-class="c-cancel--active">
					<text class='c-cancel__button' @tap='cancel'>取消</text>
				</view> 
			</view>
		</view>
		<view class='c-down' :class='[isShowList?"hidden":""]'>
			<view class='c-hot'>
				<view class='c-title'>
					<text>热门推荐</text>
				</view>
				<view class='c-tags'>
					<view class='c-tags__item' :class='[index==0?"c-tags__item--hot":""]' hover-class='c-tags__item--active' v-for="(item,index) in hotTags"
					 v-bind:key="item" @tap='selectSearchWord' :data-word="item">
						<text>{{item}}</text>
						<image src='/static/hot.png' mode='widthFix' v-if="index==0"></image>
					</view>
					<view class='c-tags__have-not-data'>
						<text :class='[isLoadHotTags==false&&isLoadHotTagsEnd==true&&hotTags.length==0?"":"hidden"]'>暂无热门推荐</text>
						<loading :is-loading="isLoadHotTags"></loading>
					</view>
				</view>
			</view>
			<view class='c-history'>
				<view class='c-title'>
					<text>历史搜索</text>
				</view>
				<view class='c-history__list'>
					<view class='c-history__item bfc bs' hover-class='c-history__item--active' v-for="(item,index) in historyTags"
					 v-bind:key="item">
						<view class='c-history__text pull-left w80_ bs' @tap='selectSearchWord' :data-word="item">
							<image class='c-history__clock' src='/static/clock.png' mode='widthFix'></image>
							<text class='pddd bfc'>{{item}}</text>
						</view>
						<view class='c-history__close pull-right w20_ text-right' :data-word="item" @tap='deleteHistoryWord'>
							<text>×</text>
						</view>
					</view>
					<view class='c-tags__have-not-data' :class='[historyTags.length!=0?"hidden":""]'>
						<text>暂无历史搜索</text>
					</view>
				</view>
			</view>
			<view class='c-clear text-center' :class='[historyTags.length==0?"hidden":""]' hover-class="c-clear--active">
				<text @tap='clearHistoryWord'>清空搜索历史</text>
			</view>
		</view>
		<view class='c-list' :class='[!isShowList?"hidden":""]'>
			<view class='c-index-content'>
				<view class='c-index-list'>
					<view v-for="(item,index) in list" v-bind:key="item.id" class='c-list-item' :class='[index==0?"c-list-item--first":""]'>
						<!-- 深度 -->
						<view v-if="!isCoin">
							<!-- <list_item item="{{item}}" bindtap="goToDetail" data-index="{{index}}"></list_item> -->
						</view>
						<!-- 币种 -->
						<view v-if="isCoin">
							<!-- <chain_item item="{{item}}" bindtap="goToDetail" data-index="{{index}}"></chain_item> -->
						</view>
						<view class='c-line bs' :class='[index==list.length-1&&index!=0?"hidden":""]'>
							<view class='c-line__content'></view>
						</view>
					</view>
					<view class='c-tip'>
						<loading :is-loading="list.length!=0&&isLoadList" text=""></loading>
						<view class='c-tip__text' :class='[isLoadList==false && isLoadEnd==true?"":"hidden"]'>
							<text>没有更多数据了...</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class='c-big-loading bs' :class='[list.length==0&&isLoadList?"":"hidden"]' :style='{
		"padding-top":paddingTop+"px"
	}'>
			<loading :is-loading="list.length==0&&isLoadList" text=""></loading>
		</view>
	</view>
</template>

<script>
	import loading from '@/components/loading/loading.vue'
	export default {
		components: {
			loading
		},
		data() {
			return {
				//关键词
				value: '',
				hotTags: [],
				isLoadHotTags: false,
				isLoadHotTagsEnd: false,
				historyTags: [],
				isShowList: false,
				paddingTop: 0,

				list: [],
				isLoadList: false,
				isLoadEnd: false,
				limit: 20,
				offset: 0,
				//是否用币种功能
				isCoin: false,
				historyWord: '',
			}
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

			onKeyInput: function(event) {
				this.value = event.target.value
			},

			cancel: function() {
				uni.navigateBack()
			},

			cancelLight: function() {
				this.setData({
					isShowList: false,
					value: ''
				})
			},

			/**
			 * 从storage加载历史关键词
			 */
			getHistorySearchWord: function() {
				var arrayListJson = uni.getStorageSync(this.historyWord);
				var result = []
				if (arrayListJson) {
					result = JSON.parse(arrayListJson)
				}
				return result
			},

			saveHistorySearchWord: function(word) {
				var list = this.getHistorySearchWord()
				var sameIndex = -1
				for (var i = 0; i < list.length; i++) {
					if (list[i] == word) {
						sameIndex = i
						break
					}
				}
				if (sameIndex != -1) {
					list.splice(sameIndex, 1)
				}
				list.unshift(word)
				this.setData({
					historyTags: list
				})
				uni.setStorageSync(this.historyWord, JSON.stringify(list))
			},

			deleteHistoryWord: function(e) {
				// console.log(e)
				var targetWord = e.currentTarget.dataset.word
				var list = this.getHistorySearchWord()
				var sameIndex = -1
				for (var i = 0; i < list.length; i++) {
					if (list[i] == targetWord) {
						sameIndex = i
						break
					}
				}
				if (sameIndex != -1) {
					list.splice(sameIndex, 1)
				}
				this.setData({
					historyTags: list
				})
				uni.setStorageSync(this.historyWord, JSON.stringify(list))
			},

			clearHistoryWord: function() {
				var list = []
				this.setData({
					historyTags: list
				})
				uni.setStorageSync(this.historyWord, JSON.stringify(list))
			},

			selectSearchWord: function(e) {
				var targetWord = e.currentTarget.dataset.word
				this.setData({
					value: targetWord
				})
				this.saveHistorySearchWord(targetWord)
				this.setData({
					isShowList: true
				})
				this.loadList(true)
			},

			confirm: function(event) {
				var value = event.detail.value
				this.setData({
					value: value
				})
				if (value) {
					this.saveHistorySearchWord(value)
				}
				this.setData({
					isShowList: true
				})
				this.loadList(true)
			},

			loadHot: function() {
				uni.showNavigationBarLoading()
				this.setData({
					isLoadHotTags: true
				})
				var self = this
				uni.request({
					url: getApp().globalData.domain + '/api/common/hot-tags',
					dataType: 'json',
					success: function(res) {
						console.log(res.data)
						uni.hideNavigationBarLoading()
						self.setData({
							hotTags: res.data,
							isLoadHotTags: false,
							isLoadHotTagsEnd: true
						})
					},
					fail: function() {
						this.setData({
							isLoadHotTags: false,
							isLoadHotTagsEnd: true
						})
						uni.hideNavigationBarLoading()
						uni.showToast({
							title: '网络错误，请重新加载',
							icon: 'none',
							duration: 2000
						})
					}
				})
			},

			/**
			 * isReset 是否重置查询
			 */
			loadList: function(isReset) {
				if (this.isLoadList == true) {
					return
				}
				if (isReset) {
					this.setData({
						offset: 0,
						isLoadEnd: false,
						isLoadList: true
					})
				} else {
					if (this.isLoadEnd) {
						return
					}
					this.setData({
						isLoadList: true
					})
				}
				uni.showNavigationBarLoading()
				var self = this
				var paramData = {
					q: this.value,
					limit: this.limit,
					offset: this.offset,
					t: this.isCoin ? 'token' : 'article'
				}
				uni.request({
					url: getApp().globalData.domain + '/api/search',
					data: paramData,
					dataType: 'json',
					success: function(res) {
						var list = res.data.rows
						// var list = []
						// for (var i = 0; i < res.data.rows.length; i++) {
						//   list.push({
						//     title: res.data.rows[i].title,
						//     tags: res.data.rows[i].tags,
						//     author: res.data.rows[i].author,
						//     updateTime: res.data.rows[i].updateTime,
						//     createTime: res.data.rows[i].createTime,
						//     reviewCount: res.data.rows[i].reviewCount,
						//     likeCount: res.data.rows[i].likeCount,
						//     preview: res.data.rows[i].preview,
						//     'type': res.data.rows[i].type,
						//     id: res.data.rows[i].id,
						//     imageArray: res.data.rows[i].imageArray
						//   })
						// }
						var obj = {
							// list: isReset ? res.data.rows : getApp().globalData.merge(self.list, res.data.rows),
							list: isReset ? list : getApp().globalData.merge(self.list, list),
							isLoadList: false,
							isLoadEnd: self.isLoadEnd,
							offset: self.offset
						}
						//判断是否可以继续下一页
						if (res.data.rows.length < self.limit) {
							obj.isLoadEnd = true
						} else {
							obj.isLoadEnd = false
							obj.offset = self.offset + self.limit
						}
						self.setData(obj)
						uni.hideNavigationBarLoading()
					},
					fail: function() {
						self.setData({
							isLoadList: false
						})
						uni.hideNavigationBarLoading()
						uni.showToast({
							title: '网络错误，请重新加载',
							icon: 'none',
							duration: 2000
						})
					}
				})
			},

			goToDetail: function(params) {
				// console.log(params)
				var index = params.target.dataset.index
				if (this.isCoin) {
					uni.navigateTo({
						url: "../chain_detail/chain_detail?id=" + this.list[index].id + "&title=" + this.list[index][
							'symbol'
						]
					})
				} else {
					uni.navigateTo({
						url: "../detail/detail?id=" + this.list[index].id + "&title=" + this.list[index].title,
					})
				}
			},
		},



		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			console.log(options)
			options.isCoin = '1'
			this.setData({
				isCoin: options.isCoin == '1',
				historyWord: options.isCoin == '1' ? getApp().globalData.storageKey.historyWordB : getApp().globalData.storageKey
					.historyWord
			})
			this.loadHot()
			this.setData({
				historyTags: this.getHistorySearchWord()
			})
			var self = this
			uni.getSystemInfo({
				success: function(res) {
					self.setData({
						paddingTop: res.windowHeight / 2 - 100
					})
				},
			})
		},

		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {

		},

		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {

		},

		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {

		},

		/**
		 * 生命周期函数--监听页面卸载
		 */
		onUnload: function() {

		},

		/**
		 * 页面相关事件处理函数--监听用户下拉动作
		 */
		onPullDownRefresh: function() {

		},

		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {
			this.loadList(false)
		},

	}
</script>

<style>
	page {
		font-size: 28rpx;
		box-sizing: border-box;
		padding-top: 82rpx;
	}
	.c-page{
		height: 100%;
	}
	.c-up {
		width: 100%;
		padding-bottom: 26rpx;
		-webkit-box-shadow: 0px 2px 4px 0px rgba(192, 192, 192, 0.5);
		box-shadow: 0px 2px 4px 0px rgba(192, 192, 192, 0.5);
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		background: white;
	}

	.c-search {
		padding: 0 28rpx;
	}

	.c-input {
		width: 578rpx;
		height: 56rpx;
		position: relative;
		background-color: #f3f3f3;
		-webkit-border-radius: 32rpx;
		border-radius: 32rpx;
	}

	.c-input__input {
		padding-left: 70rpx;
		height: 56rpx;
		font-size: 28rpx;
		color: #666;
	}

	.c-input__icon {
		position: absolute;
		left: 20rpx;
		top: 14rpx;
	}

	.c-input__icon--clear {
		position: absolute;
		top: 12rpx;
		right: 20rpx !important;
		z-index: 100;
	}

	.c-cancel {
		color: #4a90e2;
		padding-top: 6rpx;
		padding-right: 16rpx;
	}

	.c-cancel--active {
		color: #f3f3f3;
	}

	.c-cancel__button {
		font-size: 30rpx;
		display: block;
	}

	.c-hot {
		padding: 40rpx 28rpx;
	}

	.c-history {
		padding: 40rpx 28rpx;
	}

	.c-title {
		font-size: 28rpx;
		color: #333;
		font-weight: bold;
	}

	.c-tags {
		margin-top: 10rpx;
	}

	.c-tags__item {
		display: inline-block;
		background-color: #f7f7f7;
		border-radius: 20rpx;
		color: #666;
		font-size: 24rpx;
		padding: 10rpx 20rpx;
		margin-top: 20rpx;
		margin-right: 36rpx;
		position: relative;
	}

	.c-tags__item--active {
		background: lightgrey;
	}

	.c-tags__item--hot {
		padding-left: 50rpx;
	}

	.c-tags__item--hot image {
		width: 24rpx;
		position: absolute;
		left: 18rpx;
		top: 12rpx;
	}

	.c-tags__item--video {
		padding-left: 50rpx;
	}

	.c-tags__item--video image {
		width: 24rpx;
		position: absolute;
		left: 18rpx;
		top: 18rpx;
	}

	.c-history__list {
		margin-top: 10rpx;
	}

	.c-history__item {
		padding-bottom: 10rpx;
		border-bottom: 2rpx solid #eae7e7;
		padding-top: 20rpx;
	}

	.c-history__item--active {
		background: #f7f7f7;
	}

	.c-history__text {
		color: #666;
		font-size: 24rpx;
		padding-left: 48rpx;
		position: relative;
	}

	.c-history__text text {
		display: inline-block;
		width: 100%;
	}

	.c-history__clock {
		width: 30rpx;
		position: absolute;
		top: 2rpx;
		left: 0;
	}

	.c-history__close {
		color: #bbb7b7;
	}

	/* .c-tags__item--close {
  padding-right: 50rpx;
}

.c-tags__close {
  width: 40rpx;
  height: 40rpx;
  position: absolute;
  right: 8rpx;
  top: 8rpx;
  color: #d8d8d8;
  text-align: center;
}

.c-tags__close--active {
  color: #666;
} */

	.c-clear {
		font-size: 14px;
		color: #121212;
		margin-top: 60rpx;
	}

	.c-clear--active {
		color: #4a90e2;
	}

	.c-tags__have-not-data {
		text-align: center;
		color: #666;
		margin-top: 30rpx;
		font-size: 24rpx;
	}

	.c-line {
		width: 100%;
		padding: 0 30rpx;
	}

	.c-line__content {
		height: 2rpx;
		width: 100%;
		background: #efefef;
	}
</style>
