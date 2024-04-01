package webserver.requesthandler.httpParser;

import static webserver.requesthandler.http.HttpConst.HTML_FORM_DATA;
import static webserver.requesthandler.http.HttpConst.MULTIPART_FORM_DATA;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import webserver.requesthandler.http.message.Body;
import webserver.requesthandler.http.message.Headers;

public class RequestBodyParser {
    /**
     * Headers를 기반으로 HTTP 요청 본문을 추출합니다.
     *
     * @param bis     BufferedInputStream, 요청 본문이 포함된 스트림
     * @param headers HTTP 요청 헤더
     * @return HTTP 요청 본문
     * @throws IOException 본문 읽기 중 오류가 발생한 경우
     */
    public static Body extractBody(BufferedInputStream bis, Headers headers) throws IOException {
        Body body = new Body();
        if (headers.hasContentType(HTML_FORM_DATA)) {
            return extractHtmlForm(bis, headers, body);
        }
        if (headers.hasContentType(MULTIPART_FORM_DATA)) {
            return extractMultipartForm(bis, headers, body);
        }
        return body;
    }

    private static Body extractHtmlForm(BufferedInputStream bis, Headers headers, Body body) throws IOException {
        int contentLength = headers.getContentLength();
        byte[] content = new byte[contentLength];
        bis.read(content);
        body.setContent(content);
        return body;
    }

    private static Body extractMultipartForm(BufferedInputStream bis, Headers headers, Body body) throws IOException {
        int contentLength = headers.getContentLength();
        String contentType = headers.getContentType();
        byte[] boundary = ("--" + contentType.split(";\\s*")[1].split("=")[1]).getBytes();
        byte[] parts = getParts(bis, contentLength);

        int pos = 0;
        while (pos < parts.length) {
            int boundaryStart = indexOf(parts, boundary, pos); // pos부터 parts의 끝까지 바운더리가 존재하는지 확인하여 시작점 추출
            if (boundaryStart < 0) {
                break; // 바운더리를 더이상 찾을 수 없으면 종료
            }
            int boundaryEnd = indexOf(parts, "\r\n".getBytes(),
                    boundaryStart + boundary.length); // 바운더리의 종료지점 확인하여 종료점 추출
            if (boundaryEnd < 0) {
                break; // 잘못된 데이터거나 스트림의 마지막
            }
            pos = boundaryEnd + 2;

            int nextBoundaryStart = indexOf(parts, boundary, pos); // 다음 바운더리 시작점 추출
            if (nextBoundaryStart < 0) {
                nextBoundaryStart = contentLength; // 다음 바운더리가 없다면 컨텐츠의 끝점으로 설정
            }

            int partEnd = nextBoundaryStart - 2;
            if (partEnd > pos) {
                byte[] partData = Arrays.copyOfRange(parts, pos, partEnd); // \r 전까지 저장
                processPart(partData, body);
            }
            pos = nextBoundaryStart;
        }
        return body;
    }

    private static byte[] getParts(BufferedInputStream bis, int contentLength) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 임시 버퍼 크기
        int totalBytesRead = 0;
        int bytesRead;
        while (totalBytesRead < contentLength && (bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead); // 읽은 데이터를 bos에 누적
            totalBytesRead += bytesRead;
        }

        return bos.toByteArray();
    }

    private static int indexOf(byte[] parts, byte[] pattern, int start) {
        for (int i = start; i <= parts.length - pattern.length; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (parts[i + j] != pattern[j]) {
                    match = false;
                    break; // 현재 위치에서 패턴이 일치하지 않으면 내부 반복문을 종료합니다.
                }
            }
            if (match) {
                return i; // 패턴이 일치하면 현재 인덱스를 반환합니다.
            }
            // 패턴 불일치 시, 외부 반복문은 자동으로 다음 반복으로 넘어갑니다.
        }
        return -1; // 패턴을 찾지 못했으면 -1을 반환합니다.
    }


    private static void processPart(byte[] partData, Body body) {
        // partData를 바이트 배열에서 문자열로 변환합니다.
//        String partDataString = new String(partData);
        // 헤더와 데이터 분리
        int headerEnd = indexOf(partData, "\r\n\r\n".getBytes(), 0);
        if (headerEnd == -1) {
            return; // 헤더 종료 구분자가 없는 경우 무시합니다.
        }

        String header = new String(Arrays.copyOfRange(partData, 0, headerEnd));
        byte[] data = Arrays.copyOfRange(partData, headerEnd + 4, partData.length);

        // 헤더 분석
        Matcher nameMatcher = Pattern.compile("name=\"(.*?)\"").matcher(header);
        String name = nameMatcher.find() ? nameMatcher.group(1) : "";

        // 데이터 타입에 따라 body 객체에 데이터 저장
        if (name.equals("file")) {
            body.setFile(data);
        }
        if (name.equals("content")) {
            body.setContent(data);
        }
    }
}

