/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.xiuman.xingduoduo.pay.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088511350305373";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "2088511350305373";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJpqh5hqr8iK3sn1pVoFkDjTR7gnXCS/BHNXHjt5adDVs3hQi1tZSGK6HUlxrgTKkAP4Ah9SVSPm7KUvmcU9jykf0oWvPE+HczA8KshfUGfD+0XT1HJYYXxpA+2kESLQYEf8jdpzxCYPpW9Jgu7Qpusskbf1o5N05yofVElDBah/AgMBAAECgYBWspFHJj9j3XSQ9/bKwGzlOtr9rjux9f3NtJAo3FZpBlMpri9QLqTRqaVf4w/NaN3LHKg0Zcmfyrv7UuqHL7T8hrKkJPasS5MnXSmj7xFUbcS+2zv/++klo6WaPR/sx2TKe7TqkyYU7+TArNKBRNMdiLDZp2sQmMIOcoaisCo6AQJBAMoOiRpXLOiFvAiy80ksixdEORkBKy1gPVGfTe6pRagejJK+ZL4Gn4CTkGZkitXY5wNpq0pYGVo6/nPmgyBnw78CQQDDpATgoFU58IXCTtUcANQjLObToQYo6Om75W6D/nY43B1sirS+DoH7LJUoKx8puNJnVoH+qnUm3v34XmdirEtBAkEAvySDBH0FSa4BlEUdKtLNfaQXU5UscE+DkmxeP/C3qXPEzaj5Cl6fkvWFe6ePQv9egnb7CGhRC2+VeLTOxOAEZwJAVc2u0fGfz1x83Z6DetemMpMz3WdG81RLhrv2P2+v2/o18W/YG27zdPy1ojtfXIYRyX64Xr/Vx6mPS4nj3/PewQJBAIix0WWs1+EggGf+zXlr5QzwwvtizSeZlsTzQuGxBkx5Wn0EaeiMayWMEHuwgDJTRNIORdO8XnKtgD4sPUJMwJU=";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
