<script>
	export default {
		globalData: {
			domain: 'https://www.coinphd.com',
			windowHeight: 0,
			windowWidth: 0,
			//本地存储的所有的key
			storageKey: {
				//历史搜索
				historyWord: 'HISTORYWORD',
				//币种
				historyWordB: 'historyWordB',
				//openid
				openid: 'OPENID',
				//code
				code: "CODE",
				//phd_cookie
				phdCookie: "PHD_COOKIE"
			},
			//微信版本
			version: '',
			// 手机型号
			model: '',
			/**
			 * 对数组进行合并
			 */
			merge: function(oldList, targetList) {
				for (var i = 0; i < targetList.length; i++) {
					oldList.push(targetList[i])
				}
				return oldList
			},
		},
		onLaunch: function() {
			console.log('App Launch')
			var self = this
			uni.getSystemInfo({
				success: function(res) {
					console.log('getSystemInfo', res)
					self.globalData.windowHeight = res.windowHeight
					self.globalData.windowWidth = res.windowWidth
					self.globalData.version = res.version
					self.globalDatamodel = res.model
				},
			})
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
	}
</script>

<style>
	/*每个页面公共css */
	page {
		line-height: 1.43;
		font-size: 28rpx;
	}

	.main_color {
		color: white;
	}

	.main_color_bg {
		background: white;
	}

	.c-container {
		background: white;
		min-height: 100vh;
		/* height: 100vh; */
		padding: 0 20rpx;
		box-sizing: border-box;
	}

	.bfc {
		overflow: hidden;
		zoom: 1;
	}

	.bfc_x {
		overflow-x: hidden;
		zoom: 1;
	}

	.bfc_y {
		overflow-y: hidden;
		zoom: 1;
	}

	.pull-left {
		float: left;
	}

	.pull-right {
		float: right;
	}

	.w100_ {
		width: 100%;
	}

	.w90_ {
		width: 90%;
	}

	.w80_ {
		width: 80%;
	}

	.w70_ {
		width: 70%;
	}

	.w60_ {
		width: 60%;
	}

	.w50_ {
		width: 50%;
	}

	.w40_ {
		width: 40%;
	}

	.w35_ {
		width: 35%;
	}

	.w30_ {
		width: 30%;
	}

	.w333_ {
		width: 33.33%;
	}

	.w25_ {
		width: 25%;
	}

	.w20_ {
		width: 20%;
	}

	.w15_ {
		width: 15%;
	}

	.w10_ {
		width: 10%;
	}

	.text-center {
		text-align: center;
	}

	.text-right {
		text-align: right;
	}

	.block {
		display: block;
	}

	.inline-block {
		display: inline-block;
	}

	/*还原textarea的展示方式*/

	.text-div {
		white-space: pre-wrap;
		word-wrap: break-word;
	}

	.mlrauto {
		margin-left: auto;
		margin-right: auto;
	}

	.bg100_ {
		background-size: 100% 100%;
		-o-background-size: 100% 100%;
		-webkit-background-size: 100% 100%;
	}

	.bnone {
		border: none;
		outline: none;
	}

	/*省略*/

	.pddd {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.bs {
		box-sizing: border-box;
	}

	.hidden {
		display: none;
	}

	.c-tip {
		padding: 10rpx 0;
		height: 72rpx;
		min-height: 72rpx;
	}

	.c-tip__text {
		text-align: center;
	}

	.c-tip__text text {
		color: #bdbcbc;
		font-size: 26rpx;
	}

	.relative {
		position: relative;
	}
</style>
