package com.shang.layoutformat;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by wangyan-pd on 2016/8/22.
 */
public class LayoutFormat extends AnAction {

    public LayoutFormat() {
        // Set the menu item name.
        super("LayoutFormat_Action");
    }


    public void actionPerformed(final AnActionEvent event) {
        final Project project = getEventProject(event);
        VirtualFile file = event.getData(LangDataKeys.VIRTUAL_FILE);
        System.out.println("All formatted files:");
        HardStringsReplaceFromatter.getInstance().initKeyValuesMap(file);
        HardDimensReplaceFromatter.getInstance().initKeyValuesMap(file);
        execute(project, file);
        systemReformat(event);
    }


    private void systemReformat(final AnActionEvent event) {
        event.getActionManager()
                .getAction(IdeActions.ACTION_EDITOR_REFORMAT)
                .actionPerformed(event);
    }


    private void execute(Project project, final VirtualFile file) {
        VirtualFile[] files = file.getChildren();
        if (files.length > 0) {
            for (VirtualFile _file : files) {
                if (Files.isXmlFileOrDir(_file)) {
                    execute(project, _file);
                }
            }
        } else {
            System.out.println(file.getPath());
            final Document document = FileDocumentManager.getInstance().getDocument(file);
            new WriteCommandAction.Simple(project) {
                @Override protected void run() throws Throwable {
                    String txt = document.getText();
                    document.setText(Formatter.apply(txt));
                }
            }.execute();
        }
    }
//    /**
//     * 生成 contract类内容
//     * create Contract Model Presenter
//     */
//    private void setFileDocument() {
//
//
//        int lastIndex = _content.lastIndexOf("}");
//        _content = _content.substring(0, lastIndex);
//        MessagesCenter.showDebugMessage(_content, "debug");
//        final String content = setContractContent();
//        //wirte in runWriteAction
//        WriteCommandAction.runWriteCommandAction(_editor.getProject(),
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        _editor.getDocument().setText(content);
//                    }
//                });
//
//    }

    @Override public void update(AnActionEvent event) {
        super.update(event);
        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        event.getPresentation().setVisible(Files.isXmlFileOrDir(file));
    }
}
