package com.juggle.juggle.framework.data.fulltext;

import com.juggle.juggle.framework.data.entity.BaseEntity;

public interface IFulltextSearchEngine {

	void updateIndex(BaseEntity obj);

	void removeIndex(BaseEntity obj);

}