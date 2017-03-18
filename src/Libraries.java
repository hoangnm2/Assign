import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Libraries {

	public Library<Book>[] libraries; // a collection of libraries of type array
	public int numberOfLibraries; // number of libraries in collection
	
	public List<Library<Book>> foundLibs = new ArrayList<>();
	
	public Libraries(int numOfLibraries) {
		libraries = new Library[numOfLibraries];
		numberOfLibraries = 0; 
	}

	public Library<Book> buildLibraryFromFile(String libraryName, String fileName) {

		Library<Book> library = new Library<Book>(libraryName);

		String path = System.getProperty("user.dir");
		Book book = null;
		String s;

		try (BufferedReader br = new BufferedReader(new FileReader(path + "/src/" + fileName))) {
			// if you run locally on your environment use: new FileReader(path +
			// "/src/" + fileName)

			while ((s = br.readLine()) != null) {
				final String[] values = s.split(",");
				if (values.length != 2) {
					// skip the lines that are no correctly structured.
					continue;
				}
				
				try {
					book = new Book(values[0], Integer.valueOf(values[1]));
					book.setLibrary(library);
					library.addBook(book);
				} catch (final NumberFormatException e) {
					System.err.println("Error parsing at this row: " + s);
				}
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		// Add library to libraries
		libraries[numberOfLibraries] = library;
		numberOfLibraries++;
		return library;
	}

	// TODO: 2 library can have same book? Have to change class def here
	//  look for the same book in all libraries and return all the libraries where the book is in the library inventory
	public Library<Book> isThereBookInLibraries(Book book) {
		if (book == null || book.getBookName() == null) {
			System.err.println("Bad input. Cant search for non-exist book.");
			return null;
		}
		for (final Library<Book> lib : libraries) {
			//TODO: how 2 books considered equal
			for (int i=0; i<lib.getBooks().size(); i++) {
				final Book bk = (Book) lib.getBooks().get(i);
				if (bk != null && book.getBookName().equalsIgnoreCase(bk.getBookName())) {
					if (!bk.isRented(lib)) {
						return lib;
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * Return all the libraries where the book is available to be borrowed
	 * @param book
	 * @param requestDate
	 * @param dueDate
	 * @return
	 */
	public Library<Book> rentBookAvailable(Book book, String requestDate, String dueDate) {
		// Validation
		if (book == null || book.getBookName() == null) {
			System.err.println("Bad input. Cant search for non-exist book.");
			return null;
		}
		
		Library<Book> foundLibrary = null;
		for (final Library<Book> lib : libraries) {
			//TODO: how 2 books considered equal
			for (int i=0; i<lib.getBooks().size(); i++) {
				final Book bk = (Book) lib.getBooks().get(i);
				if (bk != null && book.getBookName().equalsIgnoreCase(bk.getBookName())) {
					if (bk.rs == null) {
						// Task 7, if rs is not set, mean that it can be rented immediately
						foundLibs.add(lib);
					} else {
						if (bk.isRentTimeAvailable(requestDate, dueDate)) {
							foundLibs.add(lib);
						}
					}
				}
			}
		}

		return foundLibrary;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(); 
		sb.append("Libraries: ").append(numberOfLibraries).append("\n");
		for (final Library<Book> lib : libraries) {
			sb.append(lib).append("\n");
		}
		return sb.toString();
	}
	
	
}
