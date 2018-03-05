package com.jfhealthcare.modules.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfhealthcare.common.utils.WordUtils;
import com.jfhealthcare.modules.system.entity.SysWord;
import com.jfhealthcare.modules.system.mapper.SysWordMapper;
import com.jfhealthcare.modules.system.service.SysWordService;

@Service
public class SysWordServiceImpl implements SysWordService {

	@Autowired
	private SysWordMapper sysWordMapper;
	
	@Override
	public SysWord querySimpleSysWord(String word) {
		SysWord sysWord = querySysWord(word,true);
		return sysWord;
	}

	@Override
	public SysWord queryFullSysWord(String word) {
		SysWord sysWord = querySysWord(word,false);
		return sysWord;
	}
	
	private SysWord querySysWord(String word,boolean flag){
		char[] words = word.toCharArray();
		StringBuffer sbpy=new StringBuffer();
		StringBuffer sbwb=new StringBuffer();
		for (char w : words) {
			if(!WordUtils.isChinese(w)){
				sbpy.append(StringUtils.lowerCase(String.valueOf(w)));
				sbwb.append(StringUtils.lowerCase(String.valueOf(w)));
				continue;
			}
			SysWord sysWord=new SysWord();
			sysWord.setCword(String.valueOf(w));
			List<SysWord> sysWords = sysWordMapper.select(sysWord);	
			if(sysWords.isEmpty()){
				sbpy.append("?");
				sbwb.append("?");
			}else{
				SysWord sysword = sysWords.get(0);
				if(flag){
					sbpy.append(StringUtils.isEmpty(sysword.getPycode())?"?":sysword.getPycode().charAt(0));
					sbwb.append(StringUtils.isEmpty(sysword.getWbcode())?"?":sysword.getWbcode().charAt(0));
				}else{
					sbpy.append(StringUtils.isEmpty(sysword.getPycode())?"?":sysword.getPycode());
					sbwb.append(StringUtils.isEmpty(sysword.getWbcode())?"?":sysword.getWbcode());	
				}
			}
			
		}
		SysWord sysWord=new SysWord();
		sysWord.setCword(word);
		sysWord.setPycode(sbpy.toString());
		sysWord.setWbcode(sbwb.toString());
		return sysWord;
	}
}
