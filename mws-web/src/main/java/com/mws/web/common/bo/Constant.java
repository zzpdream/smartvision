package com.mws.web.common.bo;

public class Constant {

	public final static Integer ROOT_MENU_ID = 0;

	/**
	 * 状态常量定义 1:正常 0:注销
	 * 
	 * @author ranfi
	 * 
	 */
	public enum Status {

		Enable(1, "可用"), DISABLE(0, "禁用");
		public int value;
		public String name;

		Status(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String getStatusName(int status) {
			return status == Status.Enable.value ? Status.Enable.name : Status.DISABLE.name;
		}
	}

	/**
	 * 性别
	 */
	public enum Gender {
		MALE(1, "男"), FEMALE(2, "女"), UNKNOWN(0, "未知");

		public int value;
		public String name;

		Gender(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String getGenderName(int value) {
			for (Gender gender : Gender.values()) {
				if (gender.value == value) {
					return gender.name;
				}
			}
			return "未知";
 		}
	}

	public enum MenuNode {
		ISLEAF_NODE(1, "子叶节点"), MENU_NODE(0, "目录节点");

		public final int value;
		public final String name;

		MenuNode(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String getNodeName(int status) {
			return status == MenuNode.ISLEAF_NODE.value ? MenuNode.ISLEAF_NODE.name : MenuNode.MENU_NODE.name;
		}
	}

	public enum MenuNodeType {
		TREE_START(0, "子树开始"), TYPE_CHILD_TREE(1, "树节点"), TYPE_CHILD_NODE(2, "子节点"), TREE_END(3, "子树结束");

		public final int value;
		public final String name;

		MenuNodeType(int value, String name) {
			this.value = value;
			this.name = name;
		}
	}


}
