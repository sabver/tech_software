<template>
	<view>		
		<view class='c-list-item'>
			<view class='c-list-item__title bs c-list-item__title--second'>
				<text class='block bs'>{{title}}</text>
			</view>
			<view class='c-list-item__toolbar bfc c-list-item__toolbar--lifestyle'>
				<view class='pull-left w20_'>
					<text class='c-list-item__tag c-list-item__tag--lifestyle text-center'>{{typeName}}</text>
				</view>
			</view>
		</view>			
	</view>
</template>

<script>
	export default {
		/**
		 * 组件的属性列表
		 */
		props: {
			title: {
				type: String,
				default: () => {
					return ''
				}
			},
			typeName:{
				type: String,
				default: () => {
					return ''
				}				
			}
		},
		created() {
			// console.log(this.item)
		}
	}
</script>

<style lang="scss" scoped>
	.c-list-item {
		padding: 20rpx 30rpx;
		background: white;
	}

	.c-list-item--hover {
		background: #f5f5f5;
	}

	.c-list-item__left {
		width: 430rpx;
	}

	.c-list-item__left--photo {
		width: 100%;
	}

	.c-list-item__left--top {
		width: 100%;
		margin-top: 20rpx;
	}

	.c-list-item__video {
		width: 100%;
		position: relative;
		height: 400rpx;
		margin-top: 20rpx;
	}

	.c-list-item__left--video {
		width: 100%;
	}

	.c-list-item__top {
		width: 100%;
	}

	.c-list-item__top image {
		height: 340rpx;
		-webkit-border-radius: 20rpx;
		border-radius: 20rpx;
	}

	.c-list-item__right {
		width: 260rpx;
	}

	.c-list-item__title {
		width: 100%;
		font-size: 32rpx;
		min-height: 88rpx;
		font-weight: bold;
	}

	.c-list-item__title--second {
		width: 100%;
		/* padding: 0 20rpx; */
		/* line-height: 1; */
		min-height: auto;
	}

	.c-list-item__toolbar {
		margin-top: 20rpx;
	}

	.c-list-item__toolbar--lifestyle {
		margin-top: 16rpx;
	}

	.c-list-item__toolbar--second {
		width: 100%;
		padding-left: 20rpx;
	}

	.c-list-item__tag {
		font-size: 24rpx;
		color: white;
		/* width: 40px; */
		height: 36rpx;
		line-height: 36rpx;
		background-image: -webkit-linear-gradient(left, #df6b6b 0%, #f39e7a 100%);
		background-image: linear-gradient(90deg, #df6b6b 0%, #f39e7a 100%);
		-webkit-border-radius: 6rpx;
		border-radius: 6rpx;
		display: inline-block;
		padding: 0 20rpx;
	}

	.c-list-item__tag--cm {
		background: #f7f5ec;
		color: #cdbc94;
	}

	.c-list-item__tag--lifestyle {
		background-image: -webkit-linear-gradient(left, #81c3f3 0%, #4a90e2 100%);
		background-image: linear-gradient(90deg, #81c3f3 0%, #4a90e2 100%);
	}

	.c-list-item__tag--photo {
		background-image: -webkit-linear-gradient(left, #ffb518 0%, #facd2c 100%);
		background-image: linear-gradient(90deg, #ffb518 0%, #facd2c 100%);
	}

	.c-list-item__tag--video {
		background-image: -webkit-linear-gradient(left, #ffb518 0%, #facd2c 100%);
		background-image: linear-gradient(90deg, #ffb518 0%, #facd2c 100%);
	}

	.c-list-item__tip-text {
		font-size: 24rpx;
		color: #bdbcbc;
	}

	.c-list-item__date {
		padding-top: 4rpx;
	}

	.c-list-item__zan {
		position: relative;
	}

	.c-list-item__zan image {
		position: absolute;
		top: 6rpx;
		right: 50rpx;
		width: 28rpx;
	}

	.c-list-item__imgcontainer {
		margin-top: 26rpx;
	}

	.c-list-item__img {
		padding-top: 12rpx;
	}

	.c-list-item__img image {
		width: 220rpx;
		height: 126rpx;
		-webkit-border-radius: 12rpx;
		border-radius: 12rpx;
	}

	.c-list-item__img--second image {
		width: 200rpx;
		height: 116rpx;
		-webkit-border-radius: 12rpx;
		border-radius: 12rpx;
	}

	.c-list-item__price {
		font-size: 21rpx;
		color: #f95858;
	}

	.c-list-item__video-icon {
		text-align: center;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		width: 100%;
		height: 100%;
		display: table;
	}

	.c-list-item__vwarp {
		display: table-cell;
		vertical-align: middle;
		height: 100%;
	}

	.c-list-item__vimage {
		height: 400rpx;
		width: 100%;
		-webkit-border-radius: 20rpx;
		border-radius: 20rpx;
	}

	.c-list-item__video-icon image {
		width: 150rpx;
	}

	.c-list-item__photo {
		margin-top: 20rpx;
	}

	.c-list-item__photo image {
		-webkit-border-radius: 20rpx;
		border-radius: 20rpx;
		height: 400rpx;
	}
</style>
