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