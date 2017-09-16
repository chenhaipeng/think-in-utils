package com.thinkme.utils.net;

import com.google.common.net.InetAddresses;
import com.thinkme.utils.number.NumberUtil;
import com.thinkme.utils.text.MoreStringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * InetAddress工具类，基于Guava的InetAddresses.
 * 
 * 主要包含int, String/IPV4String, InetAdress/Inet4Address之间的互相转换
 * 
 * 先将字符串传换为byte[]再用InetAddress.getByAddress(byte[])，避免了InetAddress.getByName(ip)可能引起的DNS访问.
 * 
 * InetAddress与String的转换其实消耗不小，如果是有限的地址，建议进行缓存.
 * 
 * @author calvin
 */
public class IPUtil {
	/**
	 * 从InetAddress转化到int, 传输和存储时, 用int代表InetAddress是最小的开销.
	 * 
	 * InetAddress可以是IPV4或IPV6，都会转成IPV4.
	 * 
	 * @see InetAddresses#coerceToInteger(InetAddress)
	 */
	public static int toInt(InetAddress address) {
		return InetAddresses.coerceToInteger(address);
	}

	/**
	 * InetAddress转换为String.
	 * 
	 * InetAddress可以是IPV4或IPV6. 其中IPV4直接调用getHostAddress()
	 * 
	 * @see InetAddresses#toAddrString(InetAddress)
	 */
	public static String toString(InetAddress address) {
		return InetAddresses.toAddrString(address);
	}

	/**
	 * 从int转换为Inet4Address(仅支持IPV4)
	 */
	public static Inet4Address fromInt(int address) {
		return InetAddresses.fromInteger(address);
	}

	/**
	 * 从String转换为InetAddress.
	 * 
	 * IpString可以是ipv4 或 ipv6 string, 但不可以是域名.
	 * 
	 * 先字符串传换为byte[]再调getByAddress(byte[])，避免了调用getByName(ip)可能引起的DNS访问.
	 */
	public static InetAddress fromIpString(String address) {
		return InetAddresses.forString(address);
	}

	/**
	 * 从IPv4String转换为InetAddress.
	 * 
	 * IpString如果确定ipv4, 使用本方法减少字符分析消耗 .
	 * 
	 * 先字符串传换为byte[]再调getByAddress(byte[])，避免了调用getByName(ip)可能引起的DNS访问.
	 */
	public static Inet4Address fromIpv4String(String address) {
		byte[] bytes = ip4StringToBytes(address);
		if (bytes == null) {
			return null;
		} else {
			try {
				return (Inet4Address) Inet4Address.getByAddress(bytes);
			} catch (UnknownHostException e) {
				throw new AssertionError(e);
			}
		}
	}

	/**
	 * int转换到IPV4 String, from Netty NetUtil
	 */
	public static String intToIpv4String(int i) {
		return new StringBuilder(15).append(i >> 24 & 0xff).append('.').append(i >> 16 & 0xff).append('.')
				.append(i >> 8 & 0xff).append('.').append(i & 0xff).toString();
	}

	/**
	 * Ipv4 String 转换到int
	 */
	public static int ipv4StringToInt(String ipv4Str) {
		byte[] byteAddress = ip4StringToBytes(ipv4Str);
		if (byteAddress == null) {
			return 0;
		} else {
			return NumberUtil.toInt(byteAddress);
		}
	}

	/**
	 * Ipv4 String 转换到byte[]
	 */
	private static byte[] ip4StringToBytes(String ipv4Str) {
		if (ipv4Str == null) {
			return null;
		}

		List<String> it = MoreStringUtil.split(ipv4Str, '.', 4);
		if (it.size() != 4) {
			return null;
		}

		byte[] byteAddress = new byte[4];
		for (int i = 0; i < 4; i++) {
			int tempInt = Integer.parseInt(it.get(i));
			if (tempInt > 255) {
				return null;
			}
			byteAddress[i] = (byte) tempInt;
		}
		return byteAddress;
	}

	public static String getIp(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (!validateIp(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (!validateIp(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (!validateIp(ip)) {
					ip = request.getRemoteAddr();
				}
			}
		}
		return ip;
	}

	private static boolean validateIp(String ip) {
		return (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ? false : true;
	}

	/**
	 * <p>
	 *  获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
	 *  但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了，如果通过了多级反向代理的话，
	 *  X-Forwarded-For的值并不止一个，而是一串IP值， 究竟哪个才是真正的用户端的真实IP呢？
	 *  答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *  例如：X-Forwarded-For：192.168.1.110, 192.168.1.120,
	 *  192.168.1.130, 192.168.1.100 用户真实IP为： 192.168.1.110
	 *  </p>
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				/** 根据网卡取本机配置的IP */
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ip = inet.getHostAddress();
				} catch (UnknownHostException e) {
//					logger.severe("IpHelper error." + e.toString());
				}
			}
		}
		/**
		 * 对于通过多个代理的情况， 第一个IP为客户端真实IP,多个IP按照','分割 "***.***.***.***".length() =
		 * 15
		 */
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
}
