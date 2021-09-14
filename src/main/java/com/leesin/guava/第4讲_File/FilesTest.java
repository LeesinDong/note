package com.leesin.guava.第4讲_File;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/***************************************
 * @author:Alex Wang
 * @Date:2017/10/14
 * @QQ: 532500648
 ***************************************/
public class FilesTest {

    private final String SOURCE_FILE = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\source.txt";
    private final String TARGET_FILE = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\target.txt";

    @Test
    public void testCopyFileWithGuava() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        /**
         * cr Files.copy 拷贝到一个新的文件，没有则创建
         */
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(), equalTo(true));
        // 转化成byteSource的hash还是应该相等的 md5
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(sourceHashCode.toString(), equalTo(targetHashCode.toString()));
    }

    @Test
    public void testCopyFileWithJDKNio2() throws IOException {
        /**
         * nio实现copy 略
         */
        java.nio.file.Files.copy(
                Paths.get("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources", "io", "source.txt"),
                Paths.get("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources", "io", "target.txt"),
                StandardCopyOption.REPLACE_EXISTING
        );
        File targetFile = new File(TARGET_FILE);

        assertThat(targetFile.exists(), equalTo(true));
    }

    @Test
    public void testMoveFile() throws IOException {
        try {
            //prepare for data.
            /**
             * cr Files.move() 从一个文件移动到另一个文件，没有则创建，原来的文件会删除
             */
            Files.move(new File(SOURCE_FILE), new File(TARGET_FILE));
            assertThat(new File(TARGET_FILE).exists(), equalTo(true));
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }
    }

    @Test
    public void testToString() throws IOException {

        final String expectedString = "today we will share the guava io knowledge.\n" +
                "but only for the basic usage. if you wanted to get the more details information\n" +
                "please read the guava document or source code.\n" +
                "\n" +
                "The guava source code is very cleanly and nice.";

        /**
         * cr 一行一行的读文件
         */
        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);

        String result = Joiner.on("\n").join(strings);
        assertThat(result, equalTo(expectedString));
    }

    @Test
    public void testToProcessString() throws IOException {
        /*Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8, new LineProcessor<Object>() {
        })Files.as;*/
        // [43, 79, 46, 0, 47]

        /**
         * cr readLines(lineProcessor); 读取每一行的时候进行其他的处理。 通过字符流进行读取的时候
         */
        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {

            private final List<Integer> lengthList = new ArrayList<>();

            @Override
            public boolean processLine(String line) throws IOException {
                /**
                 * 源码中如果某个是false，则break，丢弃后面的遍历
                 */
                if (line.length() == 0) {
                    return false;
                }

                lengthList.add(line.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        };
        /**
         * 具体看readLines里面的源码即可。
         */
        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);

        System.out.println(result);
    }

    @Test
    public void testFileSha() throws IOException {

        File file = new File(SOURCE_FILE);
//        Files.hash(file, Hashing.goodFastHash(128));
        /**
         * cr 文件的Hash，通过字节流拿到
         */
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode);
    }

    @Test
    public void testFileWrite() throws IOException {
        String testPath = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        String content1 = "content 1";
        /**
         * cr 写文件内容
         */
        Files.asCharSink(testFile, Charsets.UTF_8).write(content1);
        /**
         * cr 读文件内容
         */
        String actully = Files.asCharSource(testFile, Charsets.UTF_8).read();

        assertThat(actully, equalTo(content1));

        String content2 = "content 2";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content2);
        actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully, equalTo(content2));
    }

    @Test
    public void testFileAppend() throws IOException {
        String testPath = "C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\testFileAppend.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        /**
         * cr append 追加文件, 和普通的写相比就多了一个FileWriteMode.APPEND
         */
        CharSink charSink = Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write("content1");
        String actullay = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actullay, equalTo("content1"));

        // 再次write就是追加了
        charSink.write("content2");
        actullay = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actullay, equalTo("content1content2"));
    }

    @Test
    public void testTouchFile() throws IOException {
        File touchFile = new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\test\\resources\\io\\touch.txt");
        touchFile.deleteOnExit();
        /**
         * cr touch 创建文件
         */
        Files.touch(touchFile);
        assertThat(touchFile.exists(), equalTo(true));
    }

    @Test
    /**
     * 没有用到api，就是递归把文件写到了list中，略
     */
    public void testRecursive() {
        List<File> list = new ArrayList<>();
        this.recursiveList(new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\main"), list);
        list.forEach(System.out::println);
    }

    private void recursiveList(File root, List<File> fileList) {
        /*if (root.isHidden())
            return;
        fileList.add(root);
        if (!root.isFile()) {
            File[] files = root.listFiles();
            for (File f : files) {
                recursiveList(f, fileList);
            }
        }*/


        if (root.isHidden()) {
            return;
        }
        if (root.isFile()) {
            fileList.add(root);
        } else {
            File[] files = root.listFiles();
            for (File f : files) {
                recursiveList(f, fileList);
            }
        }
    }

    @Test
    public void testTreeFilesPreOrderTraversal() {
        File root = new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\main");
//        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(root);
        /**
         * cr 树结构，正序输出
         * a
         * a/b
         * c
         * c/d
         */
        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(root).filter(File::isFile);
        files.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesPostOrderTraversal() {
        File root = new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\main");
        /**
         * cr 树结构，逆序输出
         * a/b
         * a
         * c/d
         * c
         */
        FluentIterable<File> files = Files.fileTreeTraverser().postOrderTraversal(root);
        files.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesBreadthFirstTraversal() {
        File root = new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\main");
        /**
         * cr 树结构，按照宽度从小到大
         * a
         * a/b
         * a/b/c
         */
        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(root);
        files.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesChild() {
        File root = new File("C:\\Users\\wangwenjun\\IdeaProjects\\guava_programming\\src\\main");
        /**
         * cr 树结构，输出root节点的儿子节点
         * a/b
         * a/c
         */
        Iterable<File> children = Files.fileTreeTraverser().children(root);

        children.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }
}
