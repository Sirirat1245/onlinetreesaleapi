package onlinesale.onlinetree.controller;

import onlinesale.onlinetree.config.Config;
import onlinesale.onlinetree.model.service.FilesStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.InputStream;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/storages")
public class FilesAPIController {

    @Autowired
    FilesStorageService storageService;

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println("++++++++ file ++++++++");
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource(@RequestParam String imageName) throws Exception {

        Config conf = new Config();
        try {
//            log.info(conf.getStore_img() + "/" + imageName);
            InputStream in = new FileInputStream(conf.getFileStorePath() + "/" + imageName);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
