package com.rtj.framework.security;




import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AES extends Coder {

	/**
	 * 加密 AES是美国联邦政府采用的商业及政府数据加密标准， 预计将在未来几十年里代替DES在各个领域中得到广泛应用。
	 * AES提供128位密钥，因此，128位AES的加密强度是56位DES加密强度的1021倍还多。 假设可以制造一部可以在1秒内破解DES密码的机器，
	 * 那么使用这台机器破解一个128位AES密码需要大约149亿万年的时间。 （更深一步比较而言，宇宙一般被认为存在了还不到200亿年）因此可以预计，
	 * 美国国家标准局倡导的AES即将作为新标准取代DES。
	 * AES在密码学中是高级加密标准（Advanced Encryption Standard）的缩写，
	 * 该算法是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，
	 * 已经被多方分析且广为全世界所使用。最近，高级加密标准已然成为对称密钥加密中最流行的算法之一。
	 * AES 算法又称 Rijndael加密法，该算法为比利时密码学家Joan Daemen和Vincent Rijmen
	 * 所设计，结合两位作者的名字，以Rijndael命名。
	 * AES是美国国家标准技术研究所NIST旨在取代DES的21世纪的加密标准。
	 * AES 算法将成为美国新的数据加密标准而被广泛应用在各个领域中。
	 * 尽管人们对 AES还有不同的看法，但总体来说，AES 作为新一代的数据加密标准汇聚了强安全性、
	 * 高性能、高效率、易用和灵活等优点。
	 * AES 设计有三个密钥长度：128， 192，256位，相对而言，
	 * AES的128密钥比DES的56密钥强得多。
	 * AES 算法主要包括三个方面：轮变化、圈数和 密钥扩展
	 */
	
	public static Cipher cipher;
	public static final String KEY_ALGORITHM = "AES";
	public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	/*
	 * AES/CBC/NoPadding 要求 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16
	 * bytes
	 * 
	 * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 * 
	 * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 * 其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 * 除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	public static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";
	public static SecretKey secretKey;
	public static byte[] getIV() {
		String iv = "1234567812345678"; // IV length: must be 16 bytes long
		return iv.getBytes();
	}
	/**
	 * 使用AES 算法 加密，默认模式 AES/ECB
	 */
	public static void method_AES_ECB(String str) throws Exception {
		cipher = Cipher.getInstance(KEY_ALGORITHM);
		// KeyGenerator 生成aes算法密钥
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		log.debug("密钥的长度为：" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。

		log.debug("method1-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey);// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		log.debug("method1-解密后：" + new String(decrypt));

	}

	/**
	 * 使用AES 算法 加密，默认模式 AES/ECB/PKCS5Padding
	 */
	public static void method_AES_ECB_PKCS5Padding(String str) throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
		// KeyGenerator 生成aes算法密钥
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		log.debug("密钥的长度为：" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。

		log.debug("method2-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey);// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		log.debug("method2-解密后：" + new String(decrypt));

	}


	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	public static void method_AES_CBC_PKCS5Padding(String str) throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		// KeyGenerator 生成aes算法密钥
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		log.debug("密钥的长度为：" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用加密模式初始化  密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。

		log.debug("method3-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		log.debug("method3-解密后：" + new String(decrypt));

	}

	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/NoPadding 参见上面对于这种mode的数据限制
	 */
	public static void method_AES_CBC_NoPadding(String str) throws Exception {
		cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC_NoPadding);
		// KeyGenerator 生成aes算法密钥
		secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		log.debug("密钥的长度为：" + secretKey.getEncoded().length);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用加密模式初始化\ 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes(), 0, str.length()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。

		log.debug("method4-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);

		log.debug("method4-解密后：" + new String(decrypt));

	}
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(password.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();  
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	                Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // 加密   
	        } catch (NoSuchAlgorithmException e) {  
	        	log.error(e.getMessage(),e);
	        } catch (NoSuchPaddingException e) {  
	                log.error(e.getMessage(),e); 
	        } catch (InvalidKeyException e) {  
	                log.error(e.getMessage(),e); 
	        } catch (UnsupportedEncodingException e) {  
	                log.error(e.getMessage(),e); 
	        } catch (IllegalBlockSizeException e) {  
	                log.error(e.getMessage(),e); 
	        } catch (BadPaddingException e) {  
	                log.error(e.getMessage(),e); 
	        }  
	        return null;  
	} 
    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */  
    public static byte[] decrypt(byte[] content, String password) {  
            try {  
                     KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                     kgen.init(128, new SecureRandom(password.getBytes()));  
                     SecretKey secretKey = kgen.generateKey();  
                     byte[] enCodeFormat = secretKey.getEncoded();  
                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
                     Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
                    cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
                    byte[] result = cipher.doFinal(content);  
                    return result; // 加密   
            } catch (NoSuchAlgorithmException e) {  
            	log.error(e.getMessage(),e);
            } catch (NoSuchPaddingException e) {  
            	log.error(e.getMessage(),e);
            } catch (InvalidKeyException e) {  
            	log.error(e.getMessage(),e);
            } catch (IllegalBlockSizeException e) {  
            	log.error(e.getMessage(),e); 
            } catch (BadPaddingException e) {  
            	log.error(e.getMessage(),e); 
            }  
            return null;  
    }  
    
    
    
	public static void main(String[] args) throws Exception {
		method_AES_ECB("a*jal)k32J8czx囙国为国宽");
		method_AES_ECB_PKCS5Padding("a*jal)k32J8czx囙国为国宽");
		method_AES_CBC_PKCS5Padding("a*jal)k32J8czx囙国为国宽");

		method_AES_CBC_NoPadding("123456781234囙为国宽");// length = 16
		method_AES_CBC_NoPadding("12345678abcdefgh");// length = 16
		
	    String content = "test";  
	    String password = "12345678";  
	    //加密   
	    log.debug("加密前：" + content);  
	    byte[] encryptResult = encrypt(content, password);  
	    String encryptResultStr = parseByte2HexStr(encryptResult);  
	    log.debug("加密后：" + encryptResultStr);  
	    //解密   
	    byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
	    byte[] decryptResult = decrypt(decryptFrom,password);  
	    log.debug("解密后：" + new String(decryptResult));  

	}
}
