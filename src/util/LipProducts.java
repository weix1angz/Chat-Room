package util;

import java.util.HashMap;

/**
 * 
 * @author Mingjun Zha
 *
 */
public class LipProducts {
	/**
	 * 
	 */
	private HashMap<String, HashMap<String, String[]>> brandsAndCate; // the key is brand, the value is cate
	public LipProducts() {
		brandsAndCate = new HashMap<>();
		
	}
	public HashMap<String, String[]> Giorgio_Armani_beauty(){
		HashMap<String,String[]> cate = new HashMap<>();
		String cate1 = "Lipstick"; //38
		String cate2 = "LipStain"; //38
		String url2 = "https://www.sephora.com/product/lip-m"
				+ "aestro-P393411?skuId=1441583&icid2=products%20grid:p393411:product";
		String[] LipStain = {"$38",url2,"4.4 / 5 stars"};
		String url1 = "https://www.sephora.com/product/rouge-d-armani-"
				+ "matte-lipstick-P436062?icid2=products%20grid:p436062:product";
		String [] Lipstick = {"$38",url1,"4.7 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2,LipStain);
		return cate;
	}
	public HashMap<String, String[]> Fenty_Beauty_By_Rihanna() {
		HashMap<String,String[]> cate = new HashMap<>();
		String cate1 = "Lipstick"; //18
		String cate2 = "LipStain"; //24
		String url1 = "https://www.sephora.com/product/mattemoisel"
				+ "le-plush-matte-lipstick-P45874456?icid2=products%20grid:p45874"
				+ "456:product";
		String [] Lipstick = {"$18",url1,"4.4 / 5 stars"};
		String url2 = "https://www.sephora.com/product/stunna-lip-paint-P39787"
				+ "544?icid2=products%20grid:p39787544:product&skuId=2094274";
		String [] LipStain = {"$24",url2,"4.3 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2, LipStain);
		return cate;
	}
	
	public HashMap<String, String[]> Givenchy() {
		HashMap<String,String[]> cate = new HashMap<>();
		String cate1 = "Lipstick"; //37
		String cate2 = "LipBalm"; //37
		String url1 = "https://www.sephora.com/product/le-rouge-P3"
				+ "77755?icid2=products%20grid:p377755:product&skuId=1497536";
		String [] Lipstick = {"$37",url1,"4.5 / 5 stars"};
		String url2 = "https://www.sephora.com/product/l"
				+ "e-rouge-perfecto-beautifying-lip-balm-P410769?ic"
				+ "id2=products%20grid:p410769:product";
		String [] LipBalm = {"$37",url2,"4.0 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2, LipBalm);
		return cate;
	}
	
	public HashMap<String, String[]> Tom_Ford() {
		HashMap<String,String[]> cate = new HashMap<>();
		String cate1 = "Lipstick"; //55
		String cate2 = "LipGloss"; //48
		String url2 = "https://www.sephora.com/product/"
				+ "ultra-shine-lip-gloss-P422567?icid2=products%20grid:p422567:product";
		String [] LipGloss = {"$48",url2,"4.0 / 5 stars"};
		String url1 = "https://www.sephora.com/product/lip-color-"
				+ "P416057?icid2=products%20grid:p416057:product&skuId=1917228";
		String [] Lipstick = {"$55",url1,"4.5 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2, LipGloss);
		return cate;
	}
	
	
	public HashMap<String, String[]> YSL() {
		HashMap<String,String[]> cate = new HashMap<>();;
		String cate1 = "Lipstick";
		String cate2 = "LipStain";
		String url = "https://www.sephora.com/product/"
				+ "rouge-volupte-shine-oil-in-stick-lipstick-P377710?icid2="
				+ "ysl_lipwardrobe_carousel_us_ufe:p377710:product";
		String [] Lipstick = {"$38", url,"4.5 / 5 stars"};
		cate.put(cate1, Lipstick);
		url = "https://www.sephora.com/product/glossy-stain-lip-gloss-P304003"
				+ "?icid2=ysl_lipwardrobe_carousel_us_ufe:p304003:product";
		String [] LipStain = {"$37",url,"4.4 / 5 stars"};
		cate.put(cate2, LipStain);
		return cate;
	}
	
	public HashMap<String, String[]> Nars(){
		HashMap<String,String[]> cate = new HashMap<>();;
		String cate1 = "Lipstick";
		String cate2 = "LipBalm";
		String cate3 = "LipGloss";
		String url1 = "https://www.sephora.com/product/"
				+ "audacious-lipstick-P387906?icid2=products%20grid:"
				+ "p387906:product&skuId=1637164";
		String [] Lipstick = {"$34",url1,"4.6 / 5 stars"};
		String url2 = "https://www.sephora.com/product/"
				+ "orgasm-afterglow-lip-balm-P13046537?icid2=products%2"
				+ "0grid:p13046537:product";
		String [] LipBalm = {"$28",url2,"3.9 / 5 stars"};
		String url3 = "https://www.sephora.com/product/"
				+ "lip-gloss-P386666?icid2=products%20grid:"
				+ "p386666:product&skuId=1596469";
		String [] LipGloss = {"$26",url3,"4.3 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2, LipBalm);
		cate.put(cate3, LipGloss);
		return cate;	
	}
	public HashMap<String, String[]> Dior(){
		HashMap<String,String[]> cate = new HashMap<>();;
		String cate1 = "Lipstick";
		String cate2 = "LipLiner";
		String cate3 = "LipGlow";
		String url1 = "https://www.sephora.com/" //37
				+ "product/rouge-dior-ultra-rouge-lipstick-"
				+ "P436859?icid2=products%20grid:p436859:product&skuId=2104701";
		String url2 = "https://www.sephora.com/product/rouge-dior-ink-lip-"
				+ "liner-P436860?skuId=2105070&icid2=dior_whatsnew_us_productca"
				+ "rousel_ufe:p436860:product"; //32
		String url3 = "https://www.sephora.com/product/dior"
				+ "-addict-lip-glow-color-reviver-balm-P236816?skuId=2060358&"
				+ "icid2=dior_lipcollection_us_productcarousel_ufe:p236816:product"; //34
		String [] Lipstick = {"$37",url1,"4.9 / 5 stars"};
		String [] LipLiner = {"$32",url2,"3.8 / 5 stars"};
		String [] LipGlow = {"$34",url3,"4.4 / 5 stars"};
		cate.put(cate1, Lipstick);
		cate.put(cate2, LipLiner);
		cate.put(cate3, LipGlow);
		return cate;
		
	}

}
