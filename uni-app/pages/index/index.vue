<template>
	<view class="content"> 
		<view class="text-area"> 
			<button @click="isRoot">是否已root</button>
			<button @click="install">安装</button>
			<button @click="uninstall">卸载</button>
			<button @click="isRunning">是否运行</button>
			<button @click="openApp">打开</button>
			<button @click="getAppList">获取列表</button>
			<button @click="shell">运行shell命令</button>
		</view>
	</view>
</template>

<script>
	const packageManager = uni.requireNativePlugin('Fvv-UniPackageManager')
	export default {
		data() {
			return { 
				list:[],
			}
		}, 
		methods: {
			isRoot(){
				console.log(packageManager.isRoot())
			},
			install(){
				packageManager.install("/sdcard/1.apk",(res) => {
					console.log(res)
				})
			},
			uninstall(){
				packageManager.uninstall("com.example.uniplugin_packagemanager",(res) => {
					console.log(res)
				})
			},
			isRunning(){
				console.log(packageManager.isRunning("com.android.browser"));
			},
			openApp(){
				packageManager.openApp("com.android.browser")
			},
			getAppList(){
				packageManager.getAppList(res => { 
					console.log(res)
				})
			},
			shell(){ 
				packageManager.shell({
					command:"echo root",
					root:true
				},res => {
					console.log(res)
				})
			}
		}
	}
</script>

<style>
	 
</style>
