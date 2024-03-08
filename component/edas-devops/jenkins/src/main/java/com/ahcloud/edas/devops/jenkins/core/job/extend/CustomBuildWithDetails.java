package com.ahcloud.edas.devops.jenkins.core.job.extend;

import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.ConsoleLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/25 17:22
 **/
@Slf4j
public class CustomBuildWithDetails extends BuildWithDetails {
    public CustomBuildWithDetails(BuildWithDetails details) {
        super(details);
        super.setNumber(details.getNumber());
        super.setUrl(details.getUrl());
    }

    /**
     * Get build console output log as text.
     * Use this method to periodically obtain logs from jenkins and skip chunks that were already received
     *
     * @param bufferOffset offset in console lo
     * @return ConsoleLog object containing console output of the build. The line separation is done by
     * {@code CR+LF}.
     * @throws IOException in case of a failure.
     */
    @Override
    public ConsoleLog getConsoleOutputText(int bufferOffset) throws IOException {
        List<NameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("start", Integer.toString(bufferOffset)));
        String path = getUrl() + "logText/progressiveText";
        HttpResponse httpResponse = client.post_form_with_result(path, formData, true);

        Header moreDataHeader = httpResponse.getFirstHeader(MORE_DATA_HEADER);
        Header textSizeHeader = httpResponse.getFirstHeader(TEXT_SIZE_HEADER);
        String response = EntityUtils.toString(httpResponse.getEntity());
        boolean hasMoreData = false;
        if (moreDataHeader != null) {
            hasMoreData = Boolean.TRUE.toString().equals(moreDataHeader.getValue());
        }
        Integer currentBufferSize = bufferOffset;
        if (textSizeHeader != null) {
            try {
                currentBufferSize = Integer.parseInt(textSizeHeader.getValue());
            } catch (NumberFormatException e) {
                log.warn("Cannot parse buffer size for job {0} build {1}. Using current offset!", this.getDisplayName(), this.getNumber());
            }
        }
        return new ConsoleLog(response, hasMoreData, currentBufferSize);
    }
}
