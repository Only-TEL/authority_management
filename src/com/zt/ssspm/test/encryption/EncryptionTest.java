package com.zt.ssspm.test.encryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import com.zt.ssspm.util.EncryptUtil;

public class EncryptionTest {
	
	// 使用封装的EncryptUtil类测试项目的加密方式
	// encryptCode = HEX.ENCODE(RANDOM)+ HEX.ENCODE(SHA1(RANDOM+plainPsd))
	@Test
	public void encodePwd() {
		String password = "123456";
		// 生成随机数作为salt
		byte [] salt = EncryptUtil.generateSalt(8);
		String saltCode = EncryptUtil.encodeHex(salt);
		String encryptCode = saltCode + EncryptUtil.encodeHex(EncryptUtil.sha1(password.getBytes(), salt, 1024));
		// 6efcf80a1747fd752290a80c99ddb92604631f95db588ec8
		System.out.println(encryptCode);
	}
	// 测试不可逆加密算法
	@Test
	public void encodingMd5() {
		String clearCode = "123456";
		// e10adc3949ba59abbe56e057f20f883e
		System.out.println(DigestUtils.md5Hex(clearCode));
	}
	@Test
	public void encodingSha1() {
		String clearCode = "123456";
		// 7c4a8d09ca3762af61e59520943dc26494f8941b
		System.out.println(DigestUtils.shaHex(clearCode));
	}
	// 测试可逆加密算法
	@Test
	public void encodeHex() {
		String clearCode = "123456";
		// 3132 3334 3536
		System.out.println(Hex.encodeHex(clearCode.getBytes()));
	}
	@Test
	public void decodeHex() throws Exception{
		String hexCode = "313233343536";
		// 123456
		System.out.println(new String(Hex.decodeHex(hexCode.toCharArray())));
	}
	@Test
	public void encodeBase64() {
		String clearCode = "123456";
		// MTIzNDU2
		System.out.println(new String(Base64.encodeBase64(clearCode.getBytes())));
	}
	@Test
	public void decodeBase64() throws Exception{
		String baseCode = "MTIzNDU2";
		// 123456
		System.out.println(new String(Base64.decodeBase64(baseCode.getBytes())));
	}
}
