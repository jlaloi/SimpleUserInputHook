package simpleuserinputhook.windows.mouse;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.POINT;

public class MouseHookStructure extends Structure {

	public POINT point;
	public DWORD mouseData;

	protected List<String> getFieldOrder() {
		List<String> list = new ArrayList<String>();
		list.add("point");
		list.add("mouseData");
		return list;
	}

}
