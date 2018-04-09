package cn.client.arcsoft;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class AFR_FSDK_FACEINPUT extends Structure {
    
    public MRECT.ByValue rcFace;
    public int lOrient;
    
    @Override
    protected List getFieldOrder() { 
        return Arrays.asList(new String[] { 
             "rcFace", "lOrient"
        });
    }
}
