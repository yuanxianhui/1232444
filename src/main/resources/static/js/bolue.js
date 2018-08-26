//验证仅允许输入字母和_
function grapheme_(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^\w+$/i)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//文字数值校验小数点后两位
function numberic(obj) {
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^\d{0,6}(?:\.\d{0,2})?$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//n位数字
function numbern(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^[0-9]*$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//不允许输入中文
function nochine(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^[^\u4E00-\u9FA5\uF900-\uFA2D]{0,}$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//格式化table值
function isValidFmatter(cellvalue, options, rowObject){
	if("Y" == cellvalue) return "是";if("N" == cellvalue) return "否";
}
//校验是否为空
function isEmpty(obj){
	if(obj == null || obj == "" || obj == undefined){
		return true;
	}else{
		return false
	}
}
//校验18位身份证
function isCardID(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|[xX])$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//验证数字和字母
function isNuZiMu(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^[a-zA-Z0-9]+$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//验证手机号
function isMobile(obj){
	if(obj.value=="")return;if(obj.value==obj.value2)return;if(obj.value.search(/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//格式化性别
function isSexFmatter(obj){
	if(obj == "0")return "女";if(obj == "1")return "男";
}
//格式化员工状态
function staffFmatter(obj){
	if(obj=="0")return "试用期";if(obj=="1")return "正式员工";if(obj=="2")return "已离职";
}
//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
var o = {   
 "M+" : this.getMonth()+1,                 //月份   
 "d+" : this.getDate(),                    //日   
 "h+" : this.getHours(),                   //小时   
 "m+" : this.getMinutes(),                 //分   
 "s+" : this.getSeconds(),                 //秒   
 "q+" : Math.floor((this.getMonth()+3)/3), //季度   
 "S"  : this.getMilliseconds()             //毫秒   
};   
if(/(y+)/.test(fmt))   
 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
for(var k in o)   
 if(new RegExp("("+ k +")").test(fmt))   
fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
return fmt;   
}
