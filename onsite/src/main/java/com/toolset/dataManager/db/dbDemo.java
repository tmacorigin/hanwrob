package com.toolset.dataManager.db;

import android.content.Context;

/*
//在 Application 中增加如下变
	DBManager dbManager = null;
//onCreate中如下方法初始化
         dbManager = new DBManager(this);




 */
public class dbDemo {

	/*
	要求放入数据库的成员如下类似 都是 String类型即可
	public class friendMemberDataBasic {

	public String   tag;  // unique tag
	public String   teamName;
	public String 	memberName;
	public String   phoneNumber;
	public String   nickName;
	public String   comment;
	public String   pictureAddress;
	}
	*/


	public void constructTeamInfoFromDb( Context context )
	{

		/*
		DBHelper getDbHelper = DBManager.GetDbHelper( friendMemberDataBasic.class );
		
		ArrayList<Object> miList = getDbHelper.searchAllData();
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			friendMemberDataBasic dbMi = (friendMemberDataBasic)(miList.get(i));
			friendMemberData fmd = new friendMemberData(dbMi);
			//fmd.rebuildFriendMemberData(); // first rebuilt it
			addA_FriendMemberData( fmd );
				
		}
		getDbHelper.closeDB();
	*/
	}
	
	public void updateDataToDb( Context context )
	{
		/*
		DBHelper getDbHelper = DBManager.GetDbHelper(friendMemberDataBasic.class);
		
		getDbHelper.clearData();
		
		for( int i = 0 ; i < getTeamNum(); i++  )
		{
			for( int j = 0 ; j < Teams.get(i).member.size() ; j++ )
			{
				getDbHelper.add(  Teams.get(i).member.get(j).basic );
			}
			
		}
		getDbHelper.closeDB();
		*/
	}

}
