package com.juggle.juggle.primary.setting.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.setting.model.Dictionary;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryDao extends IRepo<Dictionary> {
    Dictionary findFirstByTypeAndDictKey(String type,String dictKey);
}
