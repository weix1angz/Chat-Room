package server.Bots;

import java.util.HashMap;
/**
 * This function is the collection for storing the information of color number for 
 * each brands
 * @author Mingjun Zha
 *
 */
public class Lipnum {
	private HashMap<String, String> ALipMas;
	private HashMap<String, String> ALipStick;
	private HashMap<String, String> FLipBalm;
	private HashMap<String, String> FLipStick;
	private HashMap<String, String> GLipStick;
	private HashMap<String, String> GLipBalm;
	private HashMap<String, String> TLipStick;
	private HashMap<String, String> TLipGloss;
	private HashMap<String, String> YLipStick;
	private HashMap<String, String> YLipStain;
	private HashMap<String, String> NLipStick;
	private HashMap<String, String> NLipBalm;
	private HashMap<String, String> NLipGloss;
	private HashMap<String, String> DLipStick;
	private HashMap<String, String> DLipLiner;
	private HashMap<String, String> DLipGlow;
	private Boolean ISurl = false;
	/**
	 * the constructor of lip num class, initializing each field and execute every
	 * function
	 */
	public Lipnum() {
		ALipMas = new HashMap<>();
		ALipStick = new HashMap<>();
		FLipBalm = new HashMap<>();
		FLipStick = new HashMap<>();
		GLipStick = new HashMap<>();
		GLipBalm = new HashMap<>();
		TLipStick = new HashMap<>();
		TLipGloss = new HashMap<>();
		YLipStick = new HashMap<>();
		YLipStain = new HashMap<>();
		NLipStick = new HashMap<>();
		NLipBalm = new HashMap<>();
		NLipGloss = new HashMap<>();
		DLipStick = new HashMap<>();
		DLipLiner = new HashMap<>();
		DLipGlow = new HashMap<>();
		this.ArmaniLipNum();
		this.DiorNum();
		this.GivenchyLipNum();
		this.FentyLipnum();
		this.NarsNum();
		this.YSLNum();
		this.Tom_FordNum();
	}
	/**
	 * Store the Armani Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void ArmaniLipNum() {
		ALipMas.put("507", "1755685");
		ALipMas.put("406", "1664275");
		ALipMas.put("508", "1755693");
		ALipMas.put("401", "1441591");
		ALipMas.put("402", "1441609");
		ALipMas.put("504", "1441617");
		ALipMas.put("501", "1441641");
		ALipMas.put("502", "1441658");
		ALipMas.put("200", "1441674");
		ALipMas.put("300", "1441625");
		ALipMas.put("500", "1441633");
		ALipMas.put("201", "1441682");
		ALipMas.put("202", "1441690");
		ALipStick.put("102", "2104255");
		ALipStick.put("103", "2104263");
		ALipStick.put("200", "2104289");
		ALipStick.put("201", "2104297");
		ALipStick.put("301", "2104313");
		ALipStick.put("400", "2104321");
		ALipStick.put("401", "2104339");
		ALipStick.put("402", "2104347");
		ALipStick.put("403", "2104354");
		ALipStick.put("500", "2104362");
		ALipStick.put("501", "2104370");
		ALipStick.put("502", "2104388");
		ALipStick.put("503", "2104396");
		ALipStick.put("506", "2104412");
		ALipStick.put("600", "2104420");
	}
	/**
	 * Store the Fenty Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void FentyLipnum() {
		FLipStick.put("Spanked", "2018224");
		FLipStick.put("Ma'Damn", "2018190");
		FLipStick.put("Candy-Venom", "2018216");
		FLipStick.put("Saw-C", "2018299");
		FLipStick.put("Up-2-No-Good", "2018323");
		FLipStick.put("S1ngle", "2018232");
		FLipStick.put("Freckle-Fiesta", "2018281");
		FLipStick.put("Shawty", "2018315");
		FLipBalm.put("Uncensored", "1925114");
		FLipBalm.put("Unveil", "2094266");
		FLipBalm.put("Uncuffed", "2094274");
		FLipBalm.put("Unbutton", "2094282");
		FLipBalm.put("Uninvited", "2150019");
	}
	/**
	 * Store the Givenchy Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void GivenchyLipNum() {
		GLipStick.put("204", "1602341");
		GLipStick.put("201", "1497502");
		GLipStick.put("301", "1497585");
		GLipStick.put("302", "1602358");
		GLipStick.put("321", "1862028");
		GLipStick.put("324", "1967694");
		GLipBalm.put("01", "1862002");
		GLipBalm.put("02", "2091593");
		GLipBalm.put("03", "2091585");
		GLipBalm.put("04", "2091577");
	}
	/**
	 * Store the Tom_ford Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void Tom_FordNum() {
		TLipStick.put("Naked-Coral", "1917053");
		TLipStick.put("Twist-of-Fate", "1917004");
		TLipStick.put("Flamingo", "1917251");
		TLipStick.put("Scarlet-Rouge", "1917319");
		TLipStick.put("True-Coral", "1917228");
		TLipStick.put("Dressed-To-Kill", "1986959");
		TLipStick.put("Jasmin-Rouge", "1986967");
		TLipStick.put("Cherry-Lush", "1917236");
		TLipGloss.put("01", "1987213");
		TLipGloss.put("02", "1987205");
		TLipGloss.put("03", "1987296");
		TLipGloss.put("04", "1987288");
		TLipGloss.put("05", "1987270");
		TLipGloss.put("06", "1987262");
		TLipGloss.put("07", "1987254");
		TLipGloss.put("08", "1987247");
		TLipGloss.put("09", "1987239");
		TLipGloss.put("10", "1987221");

	}
	/**
	 * Store the Ysl Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void YSLNum() {
		YLipStick.put("04", "1484773");
		YLipStick.put("06", "1484781");
		YLipStick.put("41", "1811603");
		YLipStick.put("13", "1484849");
		YLipStick.put("43", "1811587");
		YLipStick.put("46", "1811553");
		YLipStick.put("49", "1811520");
		YLipStick.put("51", "1811504");
		YLipStick.put("52", "1811496");
		// lipstain
		YLipStain.put("005", "1395193");
		YLipStain.put("007", "1395219");
		YLipStain.put("009", "1395524");
		YLipStain.put("012", "1395250");
		YLipStain.put("046", "1944867");
		YLipStain.put("103", "1511443");
		YLipStain.put("201", "1694496");
		YLipStain.put("204", "1694520");

	}
	/**
	 * Store the Nars Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void NarsNum() {
		NLipStick.put("Brigitte", "1637156");
		NLipStick.put("Natalie", "1637651");
		NLipStick.put("Juliette", "1637172");
		NLipStick.put("Apoline", "1846872");
		NLipStick.put("Claudia", "1637065");
		NLipStick.put("Greta", "1637602");
		NLipStick.put("Grace", "1637180");
		NLipStick.put("Lana", "1637206");
		NLipBalm.put("Orgasm", "2063758");
		NLipGloss.put("Chelsea-Girls", "1727007");
		NLipGloss.put("Turkish-Delight", "1596444");
		NLipGloss.put("Dolce-Vita", "1596378");
		NLipGloss.put("Orgasm", "1596428");
		NLipGloss.put("Super-Orgasm", "1596402");

	}
	/**
	 * Store the Dior Lip number into the hashmap, the lipnumber as the key
	 * the item number as the value
	 */
	public void DiorNum() {
		DLipStick.put("325", "2104800");
		DLipStick.put("450", "2104834");
		DLipStick.put("485", "2104818");
		DLipStick.put("651", "2104859");
		DLipStick.put("660", "2104867");
		DLipStick.put("770", "2104719");
		DLipStick.put("763", "2104883");
		DLipStick.put("777", "2104701");
		DLipLiner.put("028", "2105013");
		DLipLiner.put("434", "2105021");
		DLipLiner.put("770", "2105096");
		DLipLiner.put("777", "2105070");
		DLipLiner.put("851", "2105062");
		DLipLiner.put("999", "2105088");
		DLipGlow.put("101", "2060366");
		DLipGlow.put("001", "1162650");
		DLipGlow.put("004", "1572916");
		DLipGlow.put("007", "2015634");
		DLipGlow.put("005", "1781210");

	}
	/**
	 * This function is getting the color number by using brand and category
	 * @param brand
	 * @param cate
	 * @return the string of color number
	 */
	public String getNum(String brand, String cate) {
		String num = "NO such brands";
		if (brand.equals("Giorgio_Armani_beauty")) {
			if (cate.equals("Lipstick")) {
				num = ALipStick.keySet().toString();
			} else if (cate.equals("LipStain")) {
				num = ALipMas.keySet().toString();
			} else {
				num = "NO such category";
			}
		} else if (brand.equals("Fenty_Beauty_By_Rihanna")) {
			if (cate.equals("Lipstick")) {
				num = FLipStick.keySet().toString();
			} else if (cate.equals("LipStain")) {
				num = FLipBalm.keySet().toString();
			} else {
				num = "No such category";
			}
		} else if (brand.equals("Dior")) {
			if (cate.equals("Lipstick")) {
				num = DLipStick.keySet().toString();
			} else if (cate.equals("LipLiner")) {
				num = DLipLiner.keySet().toString();
			} else if (cate.equals("LipGlow")) {
				num = DLipGlow.keySet().toString();
			} else {
				num = "NO such category";
			}
		} else if (brand.equals("Givenchy")) {
			if (cate.equals("Lipstick")) {
				num = GLipStick.keySet().toString();
			} else if (cate.equals("LipBalm")) {
				num = GLipBalm.keySet().toString();
			} else {
				num = "NO such category";
			}
		} else if (brand.equals("Tom_Ford")) {
			if (cate.equals("Lipstick")) {
				num = TLipStick.keySet().toString();
			} else if (cate.equals("LipGloss")) {
				num = TLipGloss.keySet().toString();
			} else {
				num = "NO such category";
			}
			
		} else if (brand.equals("YSL")) {
			if (cate.equals("Lipstick")) {
				num = YLipStick.keySet().toString();
			} else if (cate.equals("LipStain")) {
				num = YLipStain.keySet().toString();
			} else {
				num = "No such category";
			}

		} else if (brand.equals("Nars")) {
			if (cate.equals("Lipstick")) {
				num = NLipStick.keySet().toString();
			} else if (cate.equals("LipBalm")) {
				num = NLipBalm.keySet().toString();
			} else if (cate.equals("LipGloss")) {
				num = NLipGloss.keySet().toString();
			} else {
				num = "NO such category";
			}
		}
		return num;

	}
	/**
	 * The function is using the brand, category and color number to get the image url
	 * @param brand which is the brand
	 * @param cate which is the category
	 * @param num which is the color number
	 * @return imageurl which is the imageurl of image
	 */
	public String Imageurl(String brand, String cate, String num) {
		String url = "No such Brands";
		if (brand.equals("Giorgio_Armani_beauty")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(ALipStick.containsKey(num)) {
					url += ALipStick.get(num);
					url += "-main-Lhero.jpg";
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}
			} else if (cate.equals("LipStain")) {
				if(ALipMas.containsKey(num)) {
					url += ALipMas.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}
			} else {
				url = "NO such category";
				ISurl = false;
			}
		} else if (brand.equals("Fenty_Beauty_By_Rihanna")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(FLipStick.containsKey(num)) {
					url+= FLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}
			} else if (cate.equals("LipStain")) {
				num = FLipBalm.keySet().toString();
				if(FLipBalm.containsKey(num)) {
					url += FLipBalm.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "No such category";
				ISurl = false;
			}
		} else if (brand.equals("Dior")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(DLipStick.containsKey(num)) {
					url += DLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipLiner")) {
				if(DLipLiner.containsKey(num)) {
					url += DLipLiner.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipGlow")) {
				if(DLipGlow.containsKey(num)) {
					url += DLipGlow.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "NO such category";
			}
		} else if (brand.equals("Givenchy")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(GLipStick.containsKey(num)) {
					url += GLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipBalm")) {
				if(GLipBalm.containsKey(num)) {
					url += GLipBalm.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "NO such category";
				ISurl = false;
			}
		} else if (brand.equals("Tom_Ford")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(TLipStick.containsKey(num)) {
					url += TLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipGloss")) {
				if(TLipGloss.containsKey(num)) {
					url += TLipGloss.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "NO such category";
				ISurl = false;
			}
			
		} else if (brand.equals("YSL")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(YLipStick.containsKey(num)) {
					url += YLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipStain")) {
				if(YLipStain.containsKey(num)) {
					url += YLipStain.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "No such category";
				ISurl = false;
			}

		} else if (brand.equals("Nars")) {
			url = "https://www.sephora.com/productimages/sku/s";
			if (cate.equals("Lipstick")) {
				if(NLipStick.containsKey(num)) {
					url += NLipStick.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
				
			} else if (cate.equals("LipBalm")) {
				if(NLipBalm.containsKey(num)) {
					url += NLipBalm.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else if (cate.equals("LipGloss")) {
				if(NLipGloss.containsKey(num)) {
					url += NLipGloss.get(num);
					url += "-main-Lhero.jpg";	
					ISurl = true;
				}else {
					url = "No such color number";
					ISurl = false;
				}	
			} else {
				url = "No such category";
				ISurl = false;
			}
		}
		else {
			ISurl = false;
		}
		return url;
		
	}
	/**
	 * distinguish if the url can be valid
	 * @return isurl which is a boolean value to distinguish if the url can be valid
	 */
	public Boolean geturl() {
		return ISurl;
	}


}
