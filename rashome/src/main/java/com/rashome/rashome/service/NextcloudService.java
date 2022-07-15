package com.rashome.rashome.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sardine.Sardine;
import com.github.sardine.DavResource;
import com.github.sardine.SardineFactory;
import com.github.sardine.impl.SardineException;
import com.rashome.rashome.config.NextcloudConfig;

import lombok.var;

@Service
public class NextcloudService {

    private NextcloudConfig nextcloudConfig;
    private Sardine sardine;

    public NextcloudService(NextcloudConfig nextcloudConfig){
        this.nextcloudConfig = nextcloudConfig;
        this.sardine = SardineFactory.begin(this.nextcloudConfig.getUsername(), this.nextcloudConfig.getPassword());
    }

    public void uploadFile(String[] foldersName, String fileName, byte[] data) 
    throws IOException, SardineException {
        
        createFolderIfAbsent(foldersName);

        this.sardine.put(
            appendFileUrl(foldersName, fileName), 
            data);
    }

    // use double for avoid sardineException
    // cost two http request
    private void createFolderIfAbsent(String[] folderName) 
    throws IOException, SardineException{

        
        var length = folderName.length;

        if (length != 0) {
            for (int i = 0; i < length; i++) {
                List<DavResource> davResources;

                if (i == 0) {
                    davResources = this.getAllFolders(null);
                }else{
                    davResources = this.getAllFolders(Arrays.copyOfRange(folderName, 0, i));
                }

                boolean flag = true;

                for (DavResource davResource : davResources) {
                    if (davResource.getName().equals(folderName[i])) {
                        flag = false;
                    }
                }
                if (flag) {
                    this.sardine.createDirectory(appendFolderUrl(Arrays.copyOfRange(folderName, 0, i+1)));
                }
            }
        }else{
            return;
        }
    }

    private List<DavResource> getAllFolders(String[] folderName) throws IOException, SardineException{
        return this.sardine.list(appendFolderUrl(folderName));
    }

    private String appendFolderUrl(String[] folderName) {
        if (folderName == null) {
            return this.nextcloudConfig.getBaseUrl();
        }else{
            StringBuilder tmp = new StringBuilder(this.nextcloudConfig.getBaseUrl());
            for (String string : folderName) {
                tmp.append(string);
                tmp.append("/");
            }
            return tmp.toString();
        }
        
    }

    public String appendFileUrl(String[] folderName, String fileName) {
        return appendFolderUrl(folderName) + fileName;
    }
}
